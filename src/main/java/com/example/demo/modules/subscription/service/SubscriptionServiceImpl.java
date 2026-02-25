package com.example.demo.modules.subscription.service;

import com.example.demo.modules.subscription.model.Subscription;
import com.example.demo.modules.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> findAllByIds(List<Long> ids) {
        return subscriptionRepository.findAllById(ids);
    }
}
