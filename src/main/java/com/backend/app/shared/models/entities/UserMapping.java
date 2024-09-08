package com.backend.app.shared.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_mappings")
public class UserMapping {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Nullable
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @Nullable
    private Author author;

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @Nullable
    private Customer customer;

    @JsonProperty("isActive")
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
