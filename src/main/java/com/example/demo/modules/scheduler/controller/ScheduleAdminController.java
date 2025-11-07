package com.example.demo.modules.scheduler.controller;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;
import com.example.demo.modules.scheduler.repository.ScheduleConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/schedule")
@RequiredArgsConstructor
public class ScheduleAdminController {

    private final ScheduleConfigRepository scheduleConfigRepository;

    @GetMapping
    public List<ScheduleConfig> list() {
        return scheduleConfigRepository.findAll();
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam Long id,
                                         @RequestParam String cron,
                                         @RequestParam boolean enabled) {
        ScheduleConfig cfg = scheduleConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("排程不存在"));

        cfg.setCronExpression(cron);
        cfg.setEnabled(enabled);
        scheduleConfigRepository.save(cfg);

        return ResponseEntity.ok("✅ 排程設定已更新，下次執行時生效");
    }
}
