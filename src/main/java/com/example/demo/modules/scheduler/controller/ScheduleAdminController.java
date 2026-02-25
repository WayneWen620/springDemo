package com.example.demo.modules.scheduler.controller;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;
import com.example.demo.modules.scheduler.usecase.ScheduleConfigListUseCase;
import com.example.demo.modules.scheduler.usecase.ScheduleConfigUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/schedule")
@RequiredArgsConstructor
public class ScheduleAdminController {

    private final ScheduleConfigListUseCase scheduleConfigListUseCase;
    private final ScheduleConfigUpdateUseCase scheduleConfigUpdateUseCase;

    @GetMapping
    public List<ScheduleConfig> list() {
        return scheduleConfigListUseCase.execute();
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam Long id,
                                         @RequestParam String cron,
                                         @RequestParam boolean enabled) {
        scheduleConfigUpdateUseCase.execute(id, cron, enabled);
        return ResponseEntity.ok("✅ 排程設定已更新，下次執行時生效");
    }
}
