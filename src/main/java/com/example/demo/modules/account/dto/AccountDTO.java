package com.example.demo.modules.account.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {
    private String username;
    private String password;
    private boolean enabled;
    private String roleName;
    private List<String> authorities;
    public AccountDTO(String username, String password, boolean enabled,
                      String roleName, List<String> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roleName = roleName;
        this.authorities = authorities;
    }

}
