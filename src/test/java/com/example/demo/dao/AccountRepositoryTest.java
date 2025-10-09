package com.example.demo.dao;

import com.example.demo.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveAndFind() {
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

    @Test
    public void findByName() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.findByName("Steven");
        assert found != null;
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    public void findByNameAndGender() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.findByNameAndGender("Steven","男");
        assert found != null;
        System.out.println(found.get(0).getName());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    public void findByTelephoneLike() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.findByTelephoneLike("%111%");
        assert found != null;
        System.out.println(found.get(0).getName()+":tel:"+found.get(0).getTelephone());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    public void queryName() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.queryName("Steven");
        assert found != null;
        System.out.println(found.get(0).getName()+":tel:"+found.get(0).getTelephone());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    public void queryName2() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.queryName2("Steven");
        assert found != null;
        System.out.println(found.get(0).getName()+":tel:"+found.get(0).getTelephone());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    @Transactional
    public void updateAddressById() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        Long id = accountRepository.save(account).getId();

        accountRepository.updateAddressById("Taipei",id);
        // 強制 flush session 確保資料已更新
        accountRepository.flush();

        Account found = accountRepository.findById(account.getId()).orElse(null);
        assert found != null;
        System.out.println(found.getName()+":tel:"+found.getTelephone()+":Addr:"+found.getAddress());
        assert found.getAddress().equals("Taipei");
    }
}