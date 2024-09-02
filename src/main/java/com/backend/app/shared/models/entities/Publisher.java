package com.backend.app.shared.models.entities;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "publishers")
public class Publisher implements Serializable{
    @Id
    @JsonProperty("id")
    @Column(name = "id")
    private String id;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("address")
    @Column(name = "address")
    private String address;

    @JsonProperty("phoneNumber")
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @JsonProperty("email")
    @Column(name = "email")
    private String email;

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
