package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * [權限]  自訂登入驗證邏輯
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final SystemUserDetailsService systemUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = systemUserDetailsService.loadUserByUsername(userName);
        if(passwordEncoder.matches(password,userDetails.getPassword())){
            return  new UsernamePasswordAuthenticationToken(userName,password,userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
