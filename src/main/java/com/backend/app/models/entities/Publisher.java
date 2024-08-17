package com.backend.app.models.entities;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "publishers")
public class Publisher {

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
    private Timestamp createdDate;

    @JsonProperty("createdBy")
    @Column(name = "created_by")
    private String createdBy;

    @JsonProperty("updatedDate")
    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @JsonProperty("updatedBy")
    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "publisher")
    private List<Book> book;
}
