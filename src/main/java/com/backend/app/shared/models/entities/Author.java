package com.backend.app.shared.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "authors")
public class Author implements Serializable {
    @Id
    @JsonProperty("id")
    @Column(name = "id")
    private String id;

    @JsonProperty("pseudonym")
    @Column(name = "pseudonym")
    private String pseudonym;

    @JsonProperty("firstname")
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @JsonProperty("lastname")
    @Column(name = "lastname")
    private String lastname;

    @JsonProperty("dob")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "dob")
    private LocalDate dob;

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
