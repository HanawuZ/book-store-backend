package com.backend.app.userservice.repositories.user;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.backend.app.shared.models.entities.User;
import com.backend.app.userservice.repositories.UserRepository;
import org.springframework.util.Assert;

@DataJpaTest
@Transactional
@ContextConfiguration(classes = UserRepositoryTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setId("id1");
        user.setUsername("username1");
        user.setEmail("email1");
        user.setFirstName("firstName1");
        user.setLastName("lastName1");
        user.setProfilePicture("profilePicture1");
        user.setCreatedDate(new Date());
        user.setPassword("password1");
        user.setIsUsing2FA(false);

        User savedUser = userRepository.save(user);

        Assert.notNull(savedUser, "User cannot be null");
        Assert.isTrue(savedUser.getId().equals("id1"), "User ID should be 'id1'");
        Assert.isTrue(savedUser.getUsername().equals("username1"), "User username should be 'username1'");
        Assert.isTrue(savedUser.getEmail().equals("email1"), "User email should be 'email1'");
        Assert.isTrue(savedUser.getFirstName().equals("firstName1"), "User firstName should be 'firstName1'");
        Assert.isTrue(savedUser.getLastName().equals("lastName1"), "User lastName should be 'lastName1'");
        Assert.isTrue(savedUser.getProfilePicture().equals("profilePicture1"), "User profilePicture should be 'profilePicture1'");
        Assert.isTrue(savedUser.getPassword().equals("password1"), "User password should be 'password1'");
        Assert.isTrue(savedUser.getIsUsing2FA().equals(false), "User isUsing2FA should be 'false'");
    }
}
