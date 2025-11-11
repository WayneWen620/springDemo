package com.example.demo.modules.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
}
