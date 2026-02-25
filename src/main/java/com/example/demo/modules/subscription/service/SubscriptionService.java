package com.example.demo.modules.subscription.service;

import com.example.demo.modules.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> findAllByIds(List<Long> ids);
}
