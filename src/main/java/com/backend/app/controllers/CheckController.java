package com.backend.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {
    
    @GetMapping
    public String index() {
        return "Hello, World!";
    }

    @GetMapping("/authorized")
    public String authorized() {
        return "Hello, World!, this is secured resource, you can accessed with server access token!";
    }
}
