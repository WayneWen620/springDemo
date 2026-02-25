# 🧭 務實 DDD + UseCase 模組化開發指南 (AI Generation Rules)

請根據此規範生成 Spring Boot 程式碼。本專案核心哲學為：**UseCase 編排流程、Domain 做業務決策、Service 處理資源存取。**

---

## 1. 核心架構職責 (Responsibilities)

| 層級 | 核心職責 | 嚴格禁止 |
| :--- | :--- | :--- |
| **API (Controller)** | 參數校驗、呼叫 UseCase、回傳 DTO | 寫業務邏輯、直接呼叫 Repository |
| **UseCase** | **流程編排者**：控制交易 (@Transactional)、組合 Service、呼叫 Domain 決策、發布事件 | 寫具體的業務計算邏輯 |
| **Domain** | **業務真理**：策略 (Policy)、規則 (Rule)、狀態機。純 Java 邏輯 | 依賴 DB、HTTP、或任何 Spring Bean |
| **Service** | **業務 IO**：存取 Repository、呼叫外部 Client、資料轉換 | 進行業務決策、控制複雜流程 |
| **Facade** | **模組窗口**：提供給其他模組呼叫的穩定 API | 暴露內部 Entity 或資料庫細節 |
| **Infra** | **技術細節**：Repository 實作、Mapper、外部 API 串接 | 包含任何業務規則 |

---

## 2. 目錄結構與命名 (Project Structure)
每個功能模組應遵循以下包路徑：
`modules/[module_name]/`
- `api/`: `XxxController.java`, `dto/`
- `usecase/`: `XxxUseCase.java` (命名：動詞+名詞+UseCase)
- `domain/`: `model/`, `policy/`, `rule/`
- `service/`: `reader/`, `writer/`
- `facade/`: `XxxFacade.java` (模組間唯一通訊點)
- `infra/`: `repo/`, `client/`, `mapper/`
- `events/`: `XxxEventHandler.java` (異步副作用)

---

## 3. 程式碼實作準則 (Implementation Rules)

### 3.1 依賴注入與 Lombok
* 一律使用 **建構子注入** (Constructor Injection)。
* 使用 Lombok 的 `@RequiredArgsConstructor`。
* **禁止**使用 `@Autowired` 欄位注入。

### 3.2 交易管理 (Transaction)
* `@Transactional` 僅標註在 **UseCase** 方法上。
* 跨模組副作用必須使用 `@TransactionalEventListener(phase = AFTER_COMMIT)` 處理最終一致性。

### 3.3 業務決策 (The "Domain" Rule)
* **Service 禁止含有 `if-else` 的業務邏輯**。
* 範例：
    * ❌ Service: `if (user.getScore() > 100) { ... }`
    * ✅ Domain: `if (riskPolicy.isQualified(user)) { ... }`

### 3.4 跨模組協作 (Inter-module)
* **查詢、扣額、風控**：呼叫目標模組的 `Facade` (強一致)。
* **通知、審計、統計**：發布 `ApplicationEvent` (最終一致)。

---

## 4. 程式碼範本 (Reference Template)

### UseCase 範本
```java
@Service
@RequiredArgsConstructor
public class ApproveApplicationUseCase {
    private final ApprovalService approvalService;
    private final ApprovalDomain approvalDomain;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public ApproveRes execute(ApproveCmd cmd) {
        // 1. 取資源 (Service)
        var app = approvalService.load(cmd.id());
        
        // 2. 做決策 (Domain)
        var decision = approvalDomain.approve(app.income(), app.score(), cmd.operatorId());
        
        // 3. 存狀態 (Service)
        approvalService.applyDecision(cmd.id(), decision);
        
        // 4. 發事件 (Event)
        publisher.publishEvent(new ApprovalApprovedAppEvent(cmd.id(), decision));
        
        return ApproveRes.of(cmd.id(), decision.status());
    }
}