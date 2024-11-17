package com.backend.app.catalogservice.book.models;

import java.time.LocalDate;


import lombok.Data;

@Data
public class UploadBook {

  private String isbn;

  private String title;

  private String genre;

  private String publicationYear;

  private Integer copiesAvailable;

  private Double price;

  @Override
  public String toString() {
    return "UploadBook{" +
        "isbn='" + isbn + '\'' +
        ", title='" + title + '\'' +
        ", genre='" + genre + '\'' +
        ", publicationYear=" + publicationYear +
        ", copiesAvailable=" + copiesAvailable +
        ", price=" + price +
        '}';
  }
}
