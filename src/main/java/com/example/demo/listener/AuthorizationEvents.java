package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

/**
 * 監測使用者是否登入成功或失敗
 */
@Component
@Slf4j
public class AuthorizationEvents {
    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent){
        log.error("Authorization failed for the user: {} due to: {}"
                ,deniedEvent.getAuthentication().get().getName()
        ,deniedEvent.getAuthentication().toString());
    }
}
