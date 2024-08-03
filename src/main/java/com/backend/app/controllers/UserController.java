package com.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.models.entities.User;
import com.backend.app.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {
    
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserByUsername(@RequestParam(name = "username") String username)  {
        try {
            User user = this.userService.loadUserByUsername(username);
            if (user == null) {
                // Send bad request and message
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("User not found");
            }
    
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }    
}
