package com.example.demo.modules.subscription.usecase;

import com.example.demo.common.api.APIReturnObject;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.service.AccountService;
import com.example.demo.modules.subscription.model.Subscription;
import com.example.demo.modules.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionListUseCase {

    private final SubscriptionService subscriptionService;
    private final AccountService accountService;

    public APIReturnObject execute() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString(); // 取得 username
        Account account = accountService.findByName(username); // AccountService handles not found exception
        APIReturnObject aPIReturnObject = new APIReturnObject();
        Map<String, Object> data = new HashMap<String, Object>();
        List<Subscription> subscriptions = subscriptionService.findAllByIds(Arrays.asList(account.getId()));
        aPIReturnObject.setMessage("訂閱資訊-取得成功");
        data.put("subscriptionsDatas", subscriptions);
        aPIReturnObject.setData(data);
        return aPIReturnObject;
    }
}
