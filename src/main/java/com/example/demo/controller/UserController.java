package com.example.demo.controller;

import com.example.demo.domain.Account;
import com.example.demo.domain.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    //    @RequestMapping("/list")
//    public String list(Model model) {
//        List<User> list = new ArrayList<User>();
//        list.add(new User(1,"a11",18));
//        list.add(new User(2,"b11",23));
//        list.add(new User(3,"c11",30));
//
//        model.addAttribute("list",list);
//        return "list";
//    }
    @RequestMapping("/demo1")
    public String demo(Model model) {
        model.addAttribute("message","您好,Thymeleaf");
        return "demo1";
    }

    @RequestMapping("/demo2")
    public String demo2(Model model) {
        model.addAttribute("name","alan");
        return "demo2";
    }

    @RequestMapping("/demo3")
    public String demo3(Model model) {
        model.addAttribute("gender","男");
        model.addAttribute("grade",2);


        return "demo2";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Account account) {
        return "add";
    }

    /**
     *
     * @param account
     * @param result 用於封裝驗證對象(Account)的錯誤訊息
     * @return
     */
    @RequestMapping("/add")
    public String add(@Valid Account account , BindingResult result) {
        if(result.hasErrors()){
            return "add";
        }
        System.out.println("name:"+account.getName()+";password:"+account.getPassword()+";Tel:"+account.getTelephone()+";addr:"+account.getAddress());
        return "success";
    }
}
