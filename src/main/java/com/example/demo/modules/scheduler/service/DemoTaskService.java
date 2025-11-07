package com.example.demo.modules.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DemoTaskService {

    public void run() {
        // 實際業務邏輯
        log.info(">> DemoTaskService 執行 at {}", LocalDateTime.now());

        // 這裡可以寫你的排程任務，例如：
        // 1. 清理暫存資料
        // 2. 發送通知
        // 3. 呼叫外部 API 等
    }
}
