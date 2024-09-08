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
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("firstName")
    @Column(name = "firstname")
    private String firstName;
    
    @JsonProperty("lastName")
    @Column(name = "lastname")
    @Nullable
    private String lastName;

    @JsonProperty("phoneOne")
    @Column(name = "phone_one")
    private String phoneOne;

    @JsonProperty("phoneTwo")
    @Column(name = "phone_two")
    @Nullable
    private String phoneTwo;

    @JsonProperty("createdDate")
    @Column(name = "created_date")
    private Date createdDate;

    @JsonProperty("createdBy")
    @Column(name = "created_by")
    private String createdBy;

    @JsonProperty("updatedDate")
    @Column(name = "updated_date")
    private Date updatedDate;

    @JsonProperty("updatedBy")
    @Column(name = "updated_by")
    private String updatedBy;
}
