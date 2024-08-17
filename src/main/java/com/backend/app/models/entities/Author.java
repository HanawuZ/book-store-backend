package com.backend.app.models.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.annotation.Nullable;

@Entity
@Data
@Table(name = "authors")
public class Author {
    
    @Id
    @JsonProperty("id")
    @Column(name = "id")
    private String id;

    @JsonProperty("pseudonym")
    @Column(name = "pseudonym")
    @Nullable
    private String pseudonym;

    @JsonProperty("firstname")
    @Column(name = "firstname")
    @Nullable
    private String firstname;

    @JsonProperty("lastname")
    @Column(name = "lastname")
    @Nullable
    private String lastname;

    @JsonProperty("dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob")
    @Nullable
    private LocalDate dob;

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

    @ManyToMany(mappedBy = "authors")
    Set<Book> books;
}
