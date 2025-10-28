package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws  Exception{
        //允許任何用戶,可以不需要登入
//        http.authorizeHttpRequests((request)->request.anyRequest().permitAll());
        //myAccount 會受保護,需要登入才能使用,Hello不受限制
        http.authorizeHttpRequests((request)->request.requestMatchers("/myAccount")
                .authenticated()
                .requestMatchers("/Hello").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user=User.withUsername("user").password("{noop}!QAZ@12345").authorities("read").build();
        UserDetails admin=User.withUsername("admin").password("{bcrypt}$2a$10$0rAtBWAVJxIsHPifJJAiAuTitLTx9EW67Fj/65id/JEqBIbftGxOC").authorities("admin").build();
        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     *
     * 強制使用者使用強密碼
     * 檢查使用者的密碼是否曾出現在資料外洩事件中（也就是「被洩漏過的密碼」
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
