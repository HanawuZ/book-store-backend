package com.example.catalog_svc.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "carts")
public class Cart implements Serializable {
  
  @Id
  @Column(name = "id")
  private String id;

  @ManyToOne
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private Book book;

  @Column(name = "customer_id")
  private String customerId;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "created_date")
  private Timestamp createdDate;

  @Column(name = "updated_date")
  private Timestamp updatedDate;
}
