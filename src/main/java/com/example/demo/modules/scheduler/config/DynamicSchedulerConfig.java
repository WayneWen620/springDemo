package com.example.demo.modules.scheduler.config;

import com.example.demo.modules.scheduler.entity.ScheduleConfig;

import com.example.demo.modules.scheduler.repository.ScheduleConfigRepository;
import com.example.demo.modules.scheduler.service.TaskExecutorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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
@Slf4j
public class DynamicSchedulerConfig implements SchedulingConfigurer {

    private final RedisTemplate<String, List<ScheduleConfig>> redisTemplate;
    private final ScheduleConfigRepository scheduleConfigRepository;
    private final TaskExecutorService taskExecutorService;
    public static final String SCHEDULE_REDIS_KEY = "schedule:config";
    @PostConstruct
    public void init() {
        List<ScheduleConfig> configs = scheduleConfigRepository.findAll();
        redisTemplate.opsForValue().set(SCHEDULE_REDIS_KEY, configs);
        log.info("🔄 系統啟動，Redis 初始化完成");
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        List<ScheduleConfig> configs = redisTemplate.opsForValue().get(SCHEDULE_REDIS_KEY);
        // 如果 Redis 沒資料，從 DB 拿並寫回 Redis
        if (configs == null || configs.isEmpty()) {
            configs = scheduleConfigRepository.findAll();
            redisTemplate.opsForValue().set(SCHEDULE_REDIS_KEY, configs);
            log.info("🔄 Redis 沒資料，已從 DB 讀取並寫入 Redis");
        }
        for (ScheduleConfig config : configs) {

            if (!config.isEnabled()) continue;

            taskRegistrar.addTriggerTask(
                    // 要執行的任務
                    () -> {
                       taskExecutorService.run(config.getTaskName());
                    },
                    // 觸發器
                    triggerContext -> {
                            Date next = new CronTrigger(config.getCronExpression()).nextExecutionTime(triggerContext);
                            return (next != null) ? next.toInstant() : null;
                    }
            );
        }
    }
}

