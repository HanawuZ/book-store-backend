package com.backend.app.catalogservice.book.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.app.catalogservice.book.models.ListBook;
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

  public BaseResponse<?> getBooksPaged(
    DataPagination dataPagination, 
    Integer isActive, 
    Integer minPrice, Integer maxPrice,
    List<String> genres
  ) {
    try {
      if (minPrice != null && maxPrice != null) {
        if (minPrice > maxPrice) {
          return new BaseResponse<>(4000, "Max price must be greater than min price", null);
        }
      }


      List<ListBook> results = getBookRepository.getBooksPaged(dataPagination, isActive, minPrice, maxPrice, genres);
      if (results.isEmpty()) {
        return new BaseResponse<>(4000, "No books found", results);
      }

      return new BaseResponse<>(2000, "Success", results);
    } catch (Exception exception) {
      throw exception;
    }
  }
}
