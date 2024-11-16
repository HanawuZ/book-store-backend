package com.backend.app.catalogservice.book.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UploadBook {

  private String isbn;

  private String title;

  private String genre;

  private String publicationYear;

  private Integer copiesAvailable;

  private Double price;

}
