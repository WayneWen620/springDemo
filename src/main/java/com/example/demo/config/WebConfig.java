package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 將 /login 對應到 login.html
        registry.addViewController("/login").setViewName("login");
        // 將 /logout-success 對應到 logout-success.html
        registry.addViewController("/logout-success").setViewName("logout-success");
    }
}
