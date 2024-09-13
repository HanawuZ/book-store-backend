package com.backend.app.shared.models.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "firstname")
    private String firstName;
    
    @Column(name = "lastname")
    @Nullable
    private String lastName;

    @Column(name = "phone_one")
    @Nullable
    private String phoneOne;

    @Column(name = "phone_two")
    @Nullable
    private String phoneTwo;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;
}
