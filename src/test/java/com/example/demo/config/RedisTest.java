package com.example.demo.config;

import com.example.demo.domain.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testSet() {
        redisTemplate.opsForValue().set("email", "123@gtest.com");
        Object value = redisTemplate.opsForValue().get("email");
        System.out.println("Redis value: " + value);
    }

    @Test
    void testGet() {
        String email=(String)redisTemplate.opsForValue().get("email");
        System.out.println("Redis value: " + email);
    }

    @Test
    void testSetJavaBean() {
        Account account = new Account();
        account.setTelephone("0911111111");
        account.setGender("男");
        account.setPassword("111");
        account.setAddress("New Taipei");
        account.setName("Niko");

        redisTemplate.opsForValue().set("account", account);
        Object value = redisTemplate.opsForValue().get("account");
        System.out.println("Redis value: " + account);
    }

    @Test
    void testGetJavaBean() {
        Object value = redisTemplate.opsForValue().get("account");
        Account account = new ObjectMapper().convertValue(value, Account.class);
        System.out.println("Redis value: " + account);
    }
}