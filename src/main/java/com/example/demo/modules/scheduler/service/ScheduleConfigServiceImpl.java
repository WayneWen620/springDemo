package com.example.demo.modules.scheduler.service;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;
import com.example.demo.modules.scheduler.repository.ScheduleConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleConfigServiceImpl implements ScheduleConfigService {

    private final ScheduleConfigRepository scheduleConfigRepository;

    @Override
    public List<ScheduleConfig> findAll() {
        return scheduleConfigRepository.findAll();
    }

    @Override
    public ScheduleConfig findById(Long id) {
        return scheduleConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ScheduleConfig not found, id=" + id));
    }

    @Override
    public ScheduleConfig save(ScheduleConfig scheduleConfig) {
        return scheduleConfigRepository.save(scheduleConfig);
    }
}
