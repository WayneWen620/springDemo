package com.example.demo.modules.account.dto;

import lombok.Data;

@Data
public class AccountRegisterDTO {
    private String name;
    private String gender;
    private String telephone;
    private String address;
    private String password;
    private Long roleId;
}
