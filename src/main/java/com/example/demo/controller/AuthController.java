package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class AuthController {


    @GetMapping("/home")
    public String getAccountDeteils(Model model, Authentication authentication){
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities().toString());
        }
        return "home";
    }

    @GetMapping("/invalidSession")
    @ResponseBody
    public String invalidSession() {
        return "Session 過期，請重新登入";
    }
    
}
