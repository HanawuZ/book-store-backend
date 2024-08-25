package com.backend.app.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.backend.app.models.entities.Publisher;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
@Repository
public interface PublisherRepository extends CrudRepository<Publisher, String> {

    @Query("SELECT p FROM Publisher p WHERE p.id = :publisherId")
    Optional<Publisher> getPublisherById(String publisherId);
} 