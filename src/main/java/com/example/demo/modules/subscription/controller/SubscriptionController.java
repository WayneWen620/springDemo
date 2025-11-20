package com.example.demo.modules.subscription.controller;

import com.example.demo.common.api.APIReturnObject;
import com.example.demo.modules.account.dao.AccountRepository;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.subscription.model.Subscription;
import com.example.demo.modules.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;
    private final AccountRepository accountRepository;
    @GetMapping("/all")
    public APIReturnObject getSubscriptionAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString(); // 取得 username
        Account account = accountRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        APIReturnObject aPIReturnObject = new APIReturnObject();
        Map<String, Object> data = new HashMap<String, Object>();
        List<Subscription> accounts = subscriptionRepository.findAllById(Arrays.asList(account.getId()));
        aPIReturnObject.setMessage("訂閱資訊-取得成功");
        data.put("accountsDatas", accounts);
        aPIReturnObject.setData(data);
        return aPIReturnObject;

    }
}
