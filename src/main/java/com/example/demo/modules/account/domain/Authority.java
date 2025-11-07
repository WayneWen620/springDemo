package com.example.demo.modules.account.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="authority")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name; // 如 READ, WRITE

    @Column
    private String description;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles = new HashSet<>();
}
