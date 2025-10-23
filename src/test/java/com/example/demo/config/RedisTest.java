package com.example.demo.config;

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

}