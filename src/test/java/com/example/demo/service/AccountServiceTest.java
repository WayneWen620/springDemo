package com.example.demo.service;

import com.example.demo.modules.account.dao.AccountRepository;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;


    @Test
    void findAll() {

    }

    @Test
    void findById() {
        Account account = new Account();
        account.setName("Steven");
        account.setPassword("123");
        account.setGender("男");
        account.setAddress("新北");
        account.setTelephone("0911111111");
        Long id=accountRepository.save(account).getId();
        // 告訴 mock 回傳這個 account
//        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // 執行查詢
        Account result = accountService.findById(id);

        assertNotNull(result);
        assertEquals("Steven", result.getName());
        System.out.println("查到資料：" + result.getName());
    }

    @Test
    void findByPage() {
    }

    @Test
    void save() {
    }
}