package com.example.demo.controller;

import com.example.demo.dao.AccountRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.domain.Account;
import com.example.demo.domain.Role;
import com.example.demo.dto.AccountRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/myAccount")
    public String getAccountDeteils(){
        return "Here are the account details from the DB";
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountRegisterDTO accountRegisterDTO){
        try{
            Role role = roleRepository.findById(accountRegisterDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            Account account = new Account();
            account.setName(accountRegisterDTO.getName());
            account.setGender(accountRegisterDTO.getGender());
            account.setTelephone(accountRegisterDTO.getTelephone());
            account.setAddress(accountRegisterDTO.getAddress());
            account.setPassword(passwordEncoder.encode(accountRegisterDTO.getPassword()));
            account.setEnabled(true);
            account.setRole(role);
            Account saveAccount = accountRepository.save(account);
            if(saveAccount.getId() > 0){
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Given user details are fall");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred:"+e.getMessage());
        }
    }
    @GetMapping("/invalidSession")
    @ResponseBody
    public String invalidSession() {
        return "Session 過期，請重新登入";
    }
}
