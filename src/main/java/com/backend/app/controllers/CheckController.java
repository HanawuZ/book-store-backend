package com.backend.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {
        
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public CheckController(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping
    public String index() {
        return "Hello, World!";
    }

    @GetMapping("/authorized")
    public String authorized() {
        return "Hello, World!, this is secured resource, you can accessed with server access token!";
    }

    @GetMapping("/redis/health")
    public String getValue(@RequestParam(name = "key") String key) {
        try {
            return (String) this.redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/redis/health")
    public Map.Entry<String, String> setValue(@RequestBody Map.Entry<String, String> body) {
        try {
            this.redisTemplate.opsForValue().set(body.getKey(), body.getValue());
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return Map.entry("error", e.getMessage());
        }
    }        
}
