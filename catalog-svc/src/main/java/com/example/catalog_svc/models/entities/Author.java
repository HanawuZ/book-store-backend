package com.example.catalog_svc.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
  @Column(name = "id")
  private String id;

  @Column(name = "pseudonym")
  private String pseudonym;

  @Column(name = "firstname", nullable = false)
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
  @Column(name = "dob")
  private Date dob;

  @Column(name = "created_date")
  private Timestamp createdDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_date")
  private Timestamp updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;
}