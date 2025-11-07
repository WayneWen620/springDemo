package com.example.demo.config;

import com.example.demo.modules.account.dao.AccountRepository;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.domain.Authority;
import com.example.demo.modules.account.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<String> authorityNames = account.getRole().getAuthorities().stream()
                .map(Authority::getName)
                .toList();
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 轉成 Spring Security 的 GrantedAuthority
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole().getName()));


        AccountDTO dto = new AccountDTO(
                account.getName(),
                account.getPassword(),
                account.isEnabled(),
                account.getRole().getName(),
                authorityNames
        );


        return new org.springframework.security.core.userdetails.User(
                dto.getUsername(),
                dto.getPassword(),
                dto.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
}
