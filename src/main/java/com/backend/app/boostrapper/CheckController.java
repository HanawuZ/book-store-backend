package com.backend.app.boostrapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {
    
    @GetMapping
    public String check() {
        return "Working";
    }

    @GetMapping("/authorized")
    public String checkAuthorized() {
        return "This is authorized";
    }
}
