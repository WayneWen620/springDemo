package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {
    private final SystemBasicAuthenticationEntryPoint authenticationEntryPoint;
    public ProjectSecurityConfig(SystemBasicAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //允許任何用戶,可以不需要登入
//        http.authorizeHttpRequests((request)->request.anyRequest().permitAll());
        //myAccount 會受保護,需要登入才能使用,Hello不受限制
        http
                .csrf(csrf -> csrf.disable()) // 先關掉 CSRF
                .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/Hello","/register").permitAll()                    // GET /Hello 放行
                        .requestMatchers("/myAccount").authenticated()            // 受保護
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(authenticationEntryPoint)  // ← 指定自訂 EntryPoint
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     *
     * 強制使用者使用強密碼
     * 檢查使用者的密碼是否曾出現在資料外洩事件中（也就是「被洩漏過的密碼」
     *
     * @return
     */
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker() {
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
}
