package com.example.demo.modules.scheduler.service;

import com.example.demo.modules.investment.service.TWSIOpenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class DailyTaskService {

    private final TWSIOpenService twsiOpenService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void run() {
        // 實際業務邏輯
        log.info(">> DemoTaskService 執行 at {}", LocalDateTime.now());
        twsiOpenService.schedule_AddDailyTranctionStockData();
        logger.info("上市個股日成交資訊-存入 下午五點寫入");
    }
}
