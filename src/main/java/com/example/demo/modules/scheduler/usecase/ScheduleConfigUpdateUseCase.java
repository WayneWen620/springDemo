package com.example.demo.modules.scheduler.usecase;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;
import com.example.demo.modules.scheduler.service.ScheduleConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleConfigUpdateUseCase {

    private final ScheduleConfigService scheduleConfigService;

    public void execute(Long id, String cron, boolean enabled) {
        ScheduleConfig cfg = scheduleConfigService.findById(id); // ScheduleConfigService handles not found exception

        cfg.setCronExpression(cron);
        cfg.setEnabled(enabled);
        scheduleConfigService.save(cfg);
    }
}

