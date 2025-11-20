package com.example.demo.modules.subscription.repository;

import com.example.demo.modules.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


}
