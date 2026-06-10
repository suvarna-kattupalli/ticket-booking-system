package com.Ticketing.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

   @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/redis-test")
    public String test() {

        redisTemplate.opsForValue()
                .set("test", "hello");

        return redisTemplate.opsForValue()
                .get("test");
    }
}
