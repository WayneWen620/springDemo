package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class AuthController {
    /**
     * 登入頁面
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 對應 login.html
    }

    // 登出成功頁面 (可選)
    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success"; // 對應 logout-success.html
    }

    @GetMapping("/home")
    public String getAccountDeteils(Model model, Principal principal){
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "home";
    }

    @GetMapping("/invalidSession")
    @ResponseBody
    public String invalidSession() {
        return "Session 過期，請重新登入";
    }
}
