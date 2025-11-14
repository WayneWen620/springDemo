# 專案說明
### 現況說明
#### 一、開發版本
- JAVA 17 (請使用OPENJDK)
- SPRING BOOT 3.X

#### 二、開發環境

### 專案說明
#### 一、docker
1. 啟動 MySQL + Redis docker-compose up -d
2. 查看 log docker-compose logs -f mysql 
3. 查看 log docker-compose logs -f redis 
4. 停止 docker-compose down
5. 啟動後本地會產生/mysql_data/  /redis_data/ 放 MySQL 與 Redis 的資料檔（db、log 等）
#### 一、環境
1. SPRING_PROFILES_ACTIVE=prod 正式
2. SPRING_PROFILES_ACTIVE=default 一般

# Dynamic Scheduler 模組說明

本模組使用 Spring Boot + JPA + Liquibase 實現 **動態多排程**，排程來源由資料庫管理，可動態啟用/停用、修改 cron 表達式，並自動記錄每次執行結果。

---

## 1️⃣ 資料庫結構

### schedule_config 表

| 欄位 | 型別 | 說明 |
|------|------|------|
| id | BIGINT | 主鍵，自動遞增 |
| task_name | VARCHAR(100) | 排程名稱，對應 TaskExecutorService 的任務名稱 |
| cron_expression | VARCHAR(30) | Cron 表達式，用於控制排程時間 |
| enabled | BOOLEAN | 是否啟用排程 |
| description | VARCHAR(200) | 說明欄位 |

### schedule_log 表

| 欄位 | 型別 | 說明 |
|------|------|------|
| id | BIGINT | 主鍵，自動遞增 |
| task_name | VARCHAR(100) | 執行的排程名稱 |
| run_time | TIMESTAMP | 排程實際執行時間 |
| success | BOOLEAN | 排程是否成功 |
| message | TEXT | 執行結果或錯誤訊息 |

- `modules/scheduler/`：存放排程模組相關的程式碼
    - `config/`：動態排程配置 (`DynamicSchedulerConfig.java`)
    - `service/`：排程任務與執行邏輯 (`TaskExecutorService.java`, `DemoTaskService.java`)
    - `controller/`：後台管理 API，可選
    - `repository/`：JPA Repository (`ScheduleConfigRepository.java`, `ScheduleLogRepository.java`)
    - `entity/`：排程與紀錄對應的實體類 (`ScheduleConfig.java`, `ScheduleLog.java`)
- `src/main/resources/db/changelog/`：Liquibase 變更檔
    - `db.changelog-master.xml`：主變更檔，引用其他變更檔
    - `db/common/instert_schedule_tables.xml`：建表與範例資料
--

### 新增Swagger說明

http://localhost:8080/swagger-ui/index.html