package com.example.demo.modules.account.controller;

import com.example.demo.constants.ApplicationConstants;
import com.example.demo.modules.account.dao.AccountRepository;
import com.example.demo.modules.account.dao.RoleRepository;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.domain.Role;
import com.example.demo.modules.account.dto.AccountRegisterDTO;
import com.example.demo.modules.account.dto.LoginRequestDTO;
import com.example.demo.modules.account.dto.LoginResponseDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;
    // 所有人都可以讀
    @GetMapping("/myAccount")
    public String getAccountDeteils(){
        return "Here are the account details from the DB";
    }
    // 只有 ADMIN 可以寫
    @PostMapping("/updateAccount")
//    @PreAuthorize("hasRole('ADMIN')")
    public String updateAccountDetails() {
        return "Account updated successfully";
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
    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequest) {
        String jwt = null;
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if(null != authenticationResponse && authenticationResponse.isAuthenticated()){
            if(null != env){
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder().issuer("System").subject("JWT Token")
                        .claim("username",authenticationResponse.getName())
                        .claim("authorities",authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))//設定 8.3 小時
                        .signWith(secretKey).compact();
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(),jwt));
    }
}
