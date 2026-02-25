package com.example.demo.modules.scheduler.usecase;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;
import com.example.demo.modules.scheduler.service.ScheduleConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleConfigListUseCase {

    private final ScheduleConfigService scheduleConfigService;

    public List<ScheduleConfig> execute() {
        return scheduleConfigService.findAll();
    }
}
