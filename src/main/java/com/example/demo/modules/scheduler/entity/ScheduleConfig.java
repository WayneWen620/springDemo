package com.example.demo.modules.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "schedule_config")
@Data
public class ScheduleConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private String cronExpression;
    private boolean enabled;
    private String description;
}
