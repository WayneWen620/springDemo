package com.example.demo.modules.account.dao;

import com.example.demo.modules.account.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
