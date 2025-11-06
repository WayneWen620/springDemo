package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.time.Duration;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {
    private final SystemBasicAuthenticationEntryPoint authenticationEntryPoint;
    private final SystemAccessDeniedHandler systemAccessDeniedHandler;
    public ProjectSecurityConfig(SystemBasicAuthenticationEntryPoint authenticationEntryPoint
    ,SystemAccessDeniedHandler systemAccessDeniedHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.systemAccessDeniedHandler = systemAccessDeniedHandler;
    }
    @PostConstruct
    public void init() {
        System.out.println("ProjectSecurityConfig loaded!");
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //允許任何用戶,可以不需要登入
//        http.authorizeHttpRequests((request)->request.anyRequest().permitAll());
        //myAccount 會受保護,需要登入才能使用,Hello不受限制
        http
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config=new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(csrf -> csrf.disable()) // 先關掉 CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Hello","/register","/invalidSession","/home","/logout-success").permitAll()                    // GET /Hello 放行
                        .requestMatchers("/myAccount").authenticated()            // 受保護
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")              // 告訴 Spring Login UI 在這裡
                        .loginProcessingUrl("/login")     // 表單提交處
                        .defaultSuccessUrl("/home", true)  // 登入成功導向
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-success")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(smc->smc
                        .invalidSessionUrl("/invalidSession")
                        .sessionConcurrency(concurrency ->concurrency
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                                .expiredUrl("/invalidSession")
                                .sessionRegistry(sessionRegistry())
                        )
                )
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(authenticationEntryPoint)  // ← 指定自訂 EntryPoint
                ).exceptionHandling(ehc ->ehc.accessDeniedHandler(systemAccessDeniedHandler));
        return http.build();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
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
