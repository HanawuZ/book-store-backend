package com.example.catalog_svc.book.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.catalog_svc.book.models.ListBook;
import com.example.catalog_svc.book.repositories.GetBookRepository;
import com.example.catalog_svc.models.pagination.DataPagination;
import com.example.catalog_svc.models.response.BaseResponse;

@Service
public class GetBookService {
  
  private GetBookRepository getBookRepository;

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
