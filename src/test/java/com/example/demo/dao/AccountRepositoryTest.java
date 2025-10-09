package com.example.demo.dao;

import com.example.demo.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;


    @Test
    void testSaveAndFind() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        Account found = accountRepository.findById(account.getId()).orElse(null);
        assert found != null;
        assert found.getName().equals("Steven");
    }
}