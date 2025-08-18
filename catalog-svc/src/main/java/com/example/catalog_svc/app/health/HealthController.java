package com.example.catalog_svc.app.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {

    @GetMapping
    public String health() {
        return "OK";    
    }

    @GetMapping("/authorize")
    public String authorize() {
        return "Authorized";
    }
}
