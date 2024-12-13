package com.example.catalog_svc.book.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListBook {
  
  @JsonProperty("id")
  @Column(name = "id")
  private String id;
  
  @JsonProperty("isbn")
  @Column(name = "isbn")
  private String isbn;

  @JsonProperty("title")
  @Column(name = "title")
  private String title;

  @JsonProperty("genre")
  @Column(name = "genre")
  private String genre;

  @JsonProperty("publicationYear")
  @Column(name = "publication_year")
  private Date publicationYear;

  @JsonProperty("copiesAvailable")
  @Column(name = "copies_available")
  private Integer copiesAvailable;

  @JsonProperty("price")
  @Column(name = "price")
  private Double price;

  @JsonProperty("publisher")
  @Column(name = "publisher")
  private String publisher;

  public ListBook(String id, String isbn, String title, String genre, Date publicationYear, Integer copiesAvailable, Double price, String publisher) {
    this.id = id;
    this.isbn = isbn;
    this.title = title;
    this.genre = genre;
    this.publicationYear = publicationYear;
    this.copiesAvailable = copiesAvailable;
    this.price = price;
    this.publisher = publisher;
  }
}