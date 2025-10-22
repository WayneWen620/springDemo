package com.example.demo.service;

import com.example.demo.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface AccountService {

    public List<Account> findAll();

    public Account findById(Long id);

    public Page<Account> findByPage(Pageable pageable);

    public void save(Account account);
}
