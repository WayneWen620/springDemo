package com.example.demo.dao;

import com.example.demo.modules.account.dao.RoleRepository;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OneToManyTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSave() {
        Role role =new Role();
        role.setName("一般會員");
        role.setDescription("一般會員等級");

        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");

        role.getAccounts().add(account);
        account.setRole(role);
        roleRepository.save(role);
    }

    @Test
    public void testFind() {
        Role role =new Role();
        role.setName("一般會員");
        role.setDescription("一般會員等級");

        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");

        role.getAccounts().add(account);
        account.setRole(role);
        Long id= roleRepository.save(role).getId();

        Role roleData = roleRepository.findById(id).get();
        System.out.println(roleData.getName());
    }
}
