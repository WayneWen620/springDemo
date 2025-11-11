package com.example.demo.modules.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {
    private String name;
    private String gender;
    private String telephone;
    private String address;
    private RoleDTO role;
    private boolean enabled;

}
