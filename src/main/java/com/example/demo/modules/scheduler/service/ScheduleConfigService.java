package com.example.demo.modules.scheduler.service;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;

import java.util.List;

public interface ScheduleConfigService {
    List<ScheduleConfig> findAll();
    ScheduleConfig findById(Long id);
    ScheduleConfig save(ScheduleConfig scheduleConfig);
}
