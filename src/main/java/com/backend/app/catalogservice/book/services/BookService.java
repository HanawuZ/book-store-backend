package com.backend.app.catalogservice.book.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.app.shared.libraries.csv.UploadBookCsvHelper;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.models.entities.Book;
import com.backend.app.catalogservice.book.models.UploadBook;
import com.backend.app.catalogservice.book.repositories.BookRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
  
  private UploadBookCsvHelper uploadBookCsvHelper;
  private BookRepository bookRepository;

  @Autowired
  public BookService(
    UploadBookCsvHelper uploadBookCsvHelper,
    BookRepository bookRepository
  ) {
    this.uploadBookCsvHelper = uploadBookCsvHelper;
    this.bookRepository = bookRepository;
  }

  public BaseResponse<String> createBookFromUpload(MultipartFile file) throws Exception {
    try {

      if (!uploadBookCsvHelper.hasCsvFormat(file)) {
        return new BaseResponse<String>(4000, "File is not in CSV format", null);
      }

      List<UploadBook> uploadBooks = uploadBookCsvHelper.parseCsv(file.getInputStream());
      if (uploadBooks == null || uploadBooks.isEmpty()) {
        return new BaseResponse<String>(4000, "File is empty", null);
      }

      List<Book> newBooks = new ArrayList<Book>();
      Date now = new Date();
      for (UploadBook uploadBook : uploadBooks) {
        Book book = new Book();

        book.setId(UUID.randomUUID().toString());
        book.setIsbn(uploadBook.getIsbn());
        book.setTitle(uploadBook.getTitle());
        book.setGenre(uploadBook.getGenre());
        book.setPublicationYear(uploadBook.getPublicationYear());
        book.setCopiesAvailable(uploadBook.getCopiesAvailable());
        book.setPrice(uploadBook.getPrice());
        book.setCreatedDate(now);
        book.setCreatedBy("admin");
        book.setUpdatedDate(now);
        book.setUpdatedBy("admin");
        book.setIsActive(true);
        System.out.println("BOOK:");
        System.out.println(book.toString());
        newBooks.add(book);
      }

      Boolean completed = bookRepository.createBooks(newBooks);
      if (!completed) {
        return new BaseResponse<String>(4000, "Failed to create books", null);
      }

      return new BaseResponse<String>(2001, "Success", null);

    } catch (Exception exception) {
      throw exception;
    }
  }
}
