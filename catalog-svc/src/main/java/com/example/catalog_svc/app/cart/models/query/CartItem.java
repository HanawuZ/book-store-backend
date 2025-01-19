package com.example.catalog_svc.app.cart.models.query;

import java.sql.Date;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

  @Column(name = "book_id")
  private String bookId;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "copies_available")
  private Integer copiesAvailable;

  @Column(name = "genre")
  private String genre;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "price")
  private Double price;

  @Column(name = "publication_year")
  private Date publicationYear;

  @Column(name = "title")
  private String title;

  public CartItem(String bookId, Integer quantity, Integer copiesAvailable, String genre, String isbn, Double price, Date publicationYear, String title) {
    this.bookId = bookId;
    this.quantity = quantity;
    this.copiesAvailable = copiesAvailable;
    this.genre = genre;
    this.isbn = isbn;
    this.price = price;
    this.publicationYear = publicationYear;
    this.title = title;
  }

}
