package com.example.demo.dao;

import com.example.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
