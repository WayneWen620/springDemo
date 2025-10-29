package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="role")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name; // 角色名稱，如 ADMIN, USER
    @Column(name = "description")
    private String description; // 角色描述，如「系統管理者」

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @Transient
    private List<Account> accounts = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_authority",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new HashSet<>();
}
