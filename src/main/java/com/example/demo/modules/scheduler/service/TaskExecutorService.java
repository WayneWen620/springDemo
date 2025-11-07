package com.example.demo.modules.scheduler.service;

import com.example.demo.modules.scheduler.entity.ScheduleLog;
import com.example.demo.modules.scheduler.repository.ScheduleLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskExecutorService {

    private final ScheduleLogRepository scheduleLogRepository;
    private final DemoTaskService demoTaskService; // 假設你有多個 task, 之後可再加

    public void run(String taskName) {

        ScheduleLog logRecord = new ScheduleLog();
        logRecord.setTaskName(taskName);
        logRecord.setRunTime(LocalDateTime.now());

        try {
            switch (taskName) {
                case "demoTask" -> demoTaskService.run(); // ⬅ 直接呼叫邏輯 Service
                // case "otherTask" -> otherTaskService.run();
                default -> throw new IllegalArgumentException("無法識別排程任務: " + taskName);
            }

            logRecord.setSuccess(true);
            logRecord.setMessage("成功");

        } catch (Exception e) {
            logRecord.setSuccess(false);
            logRecord.setMessage(e.getMessage());
            log.error("排程 {} 執行失敗: {}", taskName, e.getMessage(), e);
        }

        scheduleLogRepository.save(logRecord);
    }
}
