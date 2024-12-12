package com.backend.app.boostrapper.config.batch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import com.backend.app.catalogservice.book.models.UploadBook;
import com.backend.app.shared.models.entities.Book;

public class BookItemProcessor implements ItemProcessor<UploadBook, Book> {
  @Override
  public Book process(final UploadBook uploadBook) {
    try {

      if (uploadBook.getPrice() <= 0) {
        throw new IllegalArgumentException("Price must be greater than zero.");
      }

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
      LocalDate publicationYear = LocalDate.parse(uploadBook.getPublicationYear(), formatter);

      Book book = new Book();
      Date now = new Date();
      book.setId(UUID.randomUUID().toString());
      book.setIsbn(uploadBook.getIsbn());
      book.setTitle(uploadBook.getTitle());
      book.setGenre(uploadBook.getGenre());
      book.setPublicationYear(publicationYear);
      book.setCopiesAvailable(uploadBook.getCopiesAvailable());
      book.setPrice(uploadBook.getPrice());
      book.setCreatedDate(now);
      book.setCreatedBy("Bootstrapper");
      book.setUpdatedDate(now);
      book.setUpdatedBy("Bootstrapper");
      book.setIsActive(true);

      return book;
    } catch (Exception exception) {
      throw exception;
    }
  }
}
