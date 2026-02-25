package com.example.demo.modules.account.usecase;

import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.domain.Role;
import com.example.demo.modules.account.dto.AccountRegisterDTO;
import com.example.demo.modules.account.service.AccountService;
import com.example.demo.modules.account.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional // Transactional should be on UseCase methods
public class AccountRegisterUseCase {

    private final AccountService accountService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Long execute(AccountRegisterDTO accountRegisterDTO) {
        Role role = roleService.findById(accountRegisterDTO.getRoleId()); // RoleService handles not found exception
        Account account = new Account();
        account.setName(accountRegisterDTO.getName());
        account.setGender(accountRegisterDTO.getGender());
        account.setTelephone(accountRegisterDTO.getTelephone());
        account.setAddress(accountRegisterDTO.getAddress());
        account.setPassword(passwordEncoder.encode(accountRegisterDTO.getPassword()));
        account.setEnabled(true);
        account.setRole(role);
        accountService.save(account); // AccountService handles saving
        if (account.getId() > 0) { // Check ID after save
            return account.getId();
        } else {
            throw new RuntimeException("Account registration failed");
        }
    }
}
