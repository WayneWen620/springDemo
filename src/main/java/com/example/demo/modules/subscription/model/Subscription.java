package com.example.demo.modules.subscription.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private String featureType; // STOCK, WEATHER, NEWS

    private String notifyChannel; // LINE, EMAIL, SMS

    private LocalTime notifyTime;

    private Boolean enabled = true;

    @Column(columnDefinition = "JSON")
    private String config; // JSON 字串

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
