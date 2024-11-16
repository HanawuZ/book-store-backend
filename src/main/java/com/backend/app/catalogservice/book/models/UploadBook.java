package com.backend.app.catalogservice.book.models;

import java.time.LocalDate;


import lombok.Data;

@Data
public class UploadBook {

  private String isbn;

  private String title;

  private String genre;

  private LocalDate publicationYear;

  private Integer copiesAvailable;

  private Double price;

}
