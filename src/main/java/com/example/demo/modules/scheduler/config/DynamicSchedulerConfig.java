package com.example.demo.modules.scheduler.config;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;

import com.example.demo.modules.scheduler.repository.ScheduleConfigRepository;
import com.example.demo.modules.scheduler.service.TaskExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;


import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DynamicSchedulerConfig implements SchedulingConfigurer {

    private final ScheduleConfigRepository scheduleConfigRepository;
    private final TaskExecutorService taskExecutorService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        List<ScheduleConfig> configs = scheduleConfigRepository.findAll();

        for (ScheduleConfig config : configs) {

            taskRegistrar.addTriggerTask(
                    // 要執行的任務
                    () -> {
                        ScheduleConfig cfg = scheduleConfigRepository.findById(config.getId()).orElse(null);
                        if (cfg != null && cfg.isEnabled()) {
                            taskExecutorService.run(cfg.getTaskName());
                        }
                    },
                    // 觸發器
                    new Trigger() {
                        @Override
                        public java.time.Instant nextExecution(TriggerContext triggerContext) {
                            ScheduleConfig cfg = scheduleConfigRepository.findById(config.getId()).orElse(null);
                            String cron = (cfg != null) ? cfg.getCronExpression() : "0 0 * * * ?";
                            // CronTrigger 還是回傳 Date，要轉成 Instant
                            Date next = new CronTrigger(cron).nextExecutionTime(triggerContext);
                            return (next != null) ? next.toInstant() : null;
                        }
                    }
            );
        }
    }
}

