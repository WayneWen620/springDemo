package com.example.demo.modules.subscription.controller;

import com.example.demo.common.api.APIReturnObject;
import com.example.demo.modules.subscription.usecase.SubscriptionListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

        private final SubscriptionListUseCase subscriptionListUseCase;

        @GetMapping("/all")

        public APIReturnObject getSubscriptionAll() {

            return subscriptionListUseCase.execute();

        }
}
