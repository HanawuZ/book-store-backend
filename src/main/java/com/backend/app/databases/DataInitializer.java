package com.backend.app.databases;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.backend.app.models.entities.User;
import com.backend.app.repositories.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private UserRepository userRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // User user1 = new User();

        // user1.setId(UUID.randomUUID().toString());
        // user1.setFirstName("thana");
        // user1.setLastName("khan");
        // user1.setUsername("thana123");
        // user1.setPassword("thana123");
        // user1.setEmail("thana@khan");
        // user1.setProfilePicture("example.png");
        // user1.setAccountNonExpired(true);
        // user1.setAccountNonLocked(true);
        // user1.setCredentialsNonExpired(true);
        // user1.setEnabled(true);
        // user1.setCreatedDate(new Date());

        // userRepository.upsertUser(user1);
    }
}
