package com.backend.app.boostrapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.shared.models.requests.CheckRequest;

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

    @PostMapping
    public Object checkPost(@RequestBody CheckRequest request) {
        return request;
    }
}
