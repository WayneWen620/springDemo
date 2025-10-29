package com.example.demo.config;

import com.example.demo.dao.AccountRepository;
import com.example.demo.domain.Account;
import com.example.demo.domain.Authority;
import com.example.demo.domain.Role;
import com.example.demo.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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

        AccountDTO dto = new AccountDTO(
                account.getName(),
                account.getPassword(),
                account.isEnabled(),
                account.getRole().getName(),
                authorityNames
        );

        List<SimpleGrantedAuthority> authorities = dto.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

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
