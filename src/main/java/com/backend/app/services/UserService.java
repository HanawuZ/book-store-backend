package com.backend.app.services;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.backend.app.models.entities.User;
import com.backend.app.repositories.UserRepository;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) {
        try {
            Optional<User> user = userRepository.getUserByUsername(username);

            if (user.isPresent()) {
                return user.get();
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User upsertUser(Map<String, Object> userMap) {
        try {
            String userProviderId = userMap.get("providerId").toString();
            Optional<User> existedUser = userRepository.getUserByProviderId(userProviderId);
    
            User user = existedUser.orElseGet(() -> {
                User newUser = new User();
                newUser.setId(UUID.randomUUID().toString());
                newUser.setCreatedDate(new Date());
                newUser.setProvider(userMap.get("provider").toString());
                newUser.setProviderId(userProviderId);
                return newUser;
            });
    
            String fullname = (String) userMap.get("fullname");
            String[] name = fullname.split(" ");
            user.setFirstName(name[0]);
            if (name.length > 1) {
                user.setLastName(name[1]);
            }
            user.setUsername((String) userMap.get("username"));
            user.setProfilePicture((String) userMap.get("profilePicture"));
            if (userMap.get("email") != null) {
                user.setEmail((String) userMap.get("email"));
            }
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
    
            User newUser = userRepository.save(user);
            if (newUser == null) {
                return null;
            }
    
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
