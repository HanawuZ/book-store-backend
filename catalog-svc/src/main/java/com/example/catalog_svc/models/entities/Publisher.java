package com.example.catalog_svc.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "publishers")
public class Publisher implements Serializable {
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "created_date")
  private Timestamp createdDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_date")
  private Timestamp updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;

}