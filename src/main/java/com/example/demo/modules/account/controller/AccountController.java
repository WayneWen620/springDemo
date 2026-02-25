package com.example.demo.modules.account.controller;

import com.example.demo.constants.ApplicationConstants;
import com.example.demo.modules.account.dto.*;
import com.example.demo.modules.account.usecase.AccountDetailsUseCase;
import com.example.demo.modules.account.usecase.AccountRegisterUseCase;
import com.example.demo.modules.account.usecase.AuthLoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountRegisterUseCase accountRegisterUseCase;
    private final AuthLoginUseCase authLoginUseCase;
    private final AccountDetailsUseCase accountDetailsUseCase;

    // 所有人都可以讀
    @Operation(summary = "取得使用者資料", description = "")
    @GetMapping("/myAccount")
    public String getAccountDeteils() {
        return "Here are the account details from the DB";
    }

    // 只有 ADMIN 可以寫
    @Operation(summary = "更新使用者資料(特定角色可以用)", description = "")
    @PostMapping("/updateAccount")
//    @PreAuthorize("hasRole('ADMIN')")
    public String updateAccountDetails() {
        return "Account updated successfully";
    }

    @Operation(summary = "註冊", description = "")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountRegisterDTO accountRegisterDTO) {
        try {
            Long accountId = accountRegisterUseCase.execute(accountRegisterDTO);
            if (accountId > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Given user details are fall");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred:" + e.getMessage());
        }
    }
    @Operation(summary = "API登入獲得TOKEN", description = "")
    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authLoginUseCase.execute(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER, response.jwtToken())
                .body(response);
    }
    @Operation(summary = "獲取使用者資料", description = "取得使用者相關資料")
    @PostAuthorize("hasRole('USER')")
    @GetMapping("/userDetails")
    public AccountResponseDTO getUserDetails(@RequestParam long id) {
        return accountDetailsUseCase.execute(id);
    }
}
