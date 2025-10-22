package com.example.demo.service;

import com.example.demo.dao.AccountRepository;
import com.example.demo.domain.Account;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements  AccountService{
    @Autowired
    private  AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found, id=" + id));
    }

    @Override
    public Page<Account> findByPage(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }
}
