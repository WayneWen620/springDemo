package com.example.demo.modules.account.usecase;

import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.domain.Role;
import com.example.demo.modules.account.dto.AccountResponseDTO;
import com.example.demo.modules.account.dto.RoleDTO;
import com.example.demo.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Read-only transaction for fetching details
public class AccountDetailsUseCase {

    private final AccountService accountService;

    public AccountResponseDTO execute(long id) {
        Account account = accountService.findById(id); // AccountService handles not found exception
        Role role = account.getRole();
        RoleDTO roleDTO = new RoleDTO(role.getId(), role.getName(), role.getDescription());

        return new AccountResponseDTO(account.getName(),
                account.getTelephone(),
                account.getGender(),
                account.getAddress(),
                roleDTO,
                account.isEnabled());
    }
}
