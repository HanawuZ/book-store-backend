package com.backend.app.catalogservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.app.shared.models.entities.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {
    
    Page<Publisher> findAll(Pageable pageable);
} 
