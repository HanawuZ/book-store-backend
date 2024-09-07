package com.backend.app.userservice.controllers.user;

import java.util.Date;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.models.entities.User;
import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.models.SignUpRequest;
import com.backend.app.userservice.repositories.UserRepository;
import com.backend.app.userservice.services.UserService;

@TestConfiguration
public class UserControllerTestConfig {
    
    @MockBean
    private UserRepository userRepository;

    @Bean
    public UserService userService() {
        return new UserService(userRepository, null, null, null, null) {

            @Override
            public BaseResponse<String> signIn(SignInRequest signInRequest) {
                return new BaseResponse<>(2001, "Signed in successfully", "token");
            }

            @Override
            public BaseResponse<String> createUserFromSignUp(SignUpRequest signUpRequest) {
                return null;
            }

            @Override
            public User loadUserByUsername(String username) {
                User user = new User();
                user.setUsername(username);
                user.setEmail("a@a.com");
                user.setFirstName("John");
                user.setLastName("Doe");
                user.setProfilePicture("https://example.com/image.jpg");
                user.setCreatedDate(new Date());
                return user;
            }
        };
    }
}
