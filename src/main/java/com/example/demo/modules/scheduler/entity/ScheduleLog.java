package com.example.demo.modules.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule_log")
@Data
public class ScheduleLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private LocalDateTime runTime;
    private boolean success;

    @Column(columnDefinition = "TEXT")
    private String message;
}
