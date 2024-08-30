package com.backend.app.repositories;


import com.backend.app.models.entities.User;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByProviderId(String providerId);

    Optional<User> getUserByEmail(String email);

    User save(User user);

}