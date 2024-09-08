package com.backend.app.userservice.services.user;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.models.entities.User;
import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.repositories.UserRepository;
import com.backend.app.userservice.services.UserService;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.util.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
@SpringBootTest
@Import(UserServiceTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        User user1 = new User();
        user1.setId("user1");
        user1.setUsername("user1");
        user1.setEmail("a@gmail.com");
        // user1.setFirstName("John");
        // user1.setLastName("Doe");
        user1.setProfilePicture("https://example.com/image.jpg");
        user1.setCreatedDate(new Date());
        user1.setPassword(passwordEncoder.encode("123456"));
        user1.setIsUsing2FA(false);

        User userWith2FA = new User();
        userWith2FA.setId("userWith2FA");
        userWith2FA.setUsername("userWith2FA");
        userWith2FA.setEmail("b@gmail.com");
        // userWith2FA.setFirstName("Jane");
        // userWith2FA.setLastName("Doe");
        userWith2FA.setProfilePicture("https://example.com/image.jpg");
        userWith2FA.setCreatedDate(new Date());
        userWith2FA.setPassword(passwordEncoder.encode("54321"));
        userWith2FA.setIsUsing2FA(true);

        // when(userRepository.findAll()).thenReturn(Arrays.asList(user1, userWith2FA));
        // when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user1));
        // when(userRepository.findByUsername("userWith2FA")).thenReturn(Optional.of(userWith2FA));
    }

    // <Action>_<Predicate>_<Result>_<Reason>
    @Test
    @Order(1)
    void SigninWithUsernameUser1_ShouldSuccess_FailedToSignin_BecauseInvalidPassword() {

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("user1");
        signInRequest.setPassword("12345");

        BaseResponse<String> response = userService.signIn(signInRequest);

        System.out.println(response.getCode()+" "+response.getData()+" "+response.getMessage());
        Assert.isTrue(response.getCode() == 4001, "Code should be 4001");
        Assert.isTrue(response.getData() == null, "No data should be returned");
        Assert.isTrue(response.getMessage().equals("Invalid password!"), "Message should be Invalid password!");
    }

    @Test
    @Order(2)
    void SignInWithUsernameUserWith2FA_ShouldSuccess() {

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("user1");
        signInRequest.setPassword("123456");

        BaseResponse<String> response = userService.signIn(signInRequest);

        System.out.println(response.getCode()+" "+response.getData()+" "+response.getMessage());
        Assert.isTrue(response.getCode() == 2001, "Code should be 2001");
        Assert.isTrue(response.getData().equals("jwt-token"), "Data should be returned");
        Assert.isTrue(response.getMessage().equals("Signed in successfully"), "Message should be Signed in successfully");
    }

    @Test
    @Order(3)
    void SignInWithUsernameUser1With6Times_AccountLocked() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("userWith2FA");
        signInRequest.setPassword("12345");
        BaseResponse<String> response = new BaseResponse<String>(null, null, null);
        
        for (int i = 0; i < 6; i++) {
            response = userService.signIn(signInRequest);
        }

        Assert.isTrue(response.getCode() == 4290, "Code should be 4290");
        Assert.isTrue(response.getData() == null, "No data should be returned");
        Assert.isTrue(response.getMessage().equals("Too many login attempts"), "Message should be 'Too many login attempts'");
    }
    
}
