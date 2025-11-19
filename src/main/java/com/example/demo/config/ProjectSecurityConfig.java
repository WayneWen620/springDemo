package com.example.demo.config;

import com.example.demo.filter.CsrfCookieFilter;
import com.example.demo.filter.JWTTokenGeneratorFilter;
import com.example.demo.filter.JWTTokenValidatorFilter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

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
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler=new CsrfTokenRequestAttributeHandler();
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
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        return config;
                    }
                }))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers("/register","/updateAccount","/apiLogin","/swagger-ui/**","/v3/api-docs/**","/api/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) // 先關掉 CSRF
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
                        .requestMatchers("/Hello","/register","/invalidSession","/home","/logout-success","/apiLogin").permitAll()                    // GET /Hello 放行
                        .requestMatchers("/updateAccount").hasAnyRole("ADMIN")
                        .requestMatchers("/myAccount","/api/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/userDetails").authenticated()
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
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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

    @Bean
    public AuthenticationManager authenticationManager(SystemUserDetailsService userDetailsService
            ,PasswordEncoder passwordEncoder){
        SystemUsernamePwdAuthenticationProvider authenticationProvider =
                new SystemUsernamePwdAuthenticationProvider(userDetailsService,passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return  providerManager;
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
