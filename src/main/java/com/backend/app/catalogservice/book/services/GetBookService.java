package com.backend.app.catalogservice.book.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.app.catalogservice.book.models.ListBook;
import com.backend.app.catalogservice.book.repositories.BookRepository;
import com.backend.app.catalogservice.book.repositories.GetBookRepository;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.models.pagination.DataPagination;

@Service
public class GetBookService {
  
  private GetBookRepository getBookRepository;

  @Autowired
  public GetBookService(GetBookRepository bookRepository) {
    this.getBookRepository = bookRepository;
  }

  public BaseResponse<?> getBooksPaged(DataPagination dataPagination, Integer isActive) {
    try {

      List<ListBook> results = getBookRepository.getBooksPaged(dataPagination, isActive);
      if (results.isEmpty()) {
        return new BaseResponse<>(4000, "No books found", results);
      }

      return new BaseResponse<>(2000, "Success", results);
    } catch (Exception exception) {
      throw exception;
    }
  }
}
