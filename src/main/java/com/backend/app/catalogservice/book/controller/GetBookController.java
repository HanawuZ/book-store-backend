package com.backend.app.catalogservice.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.catalogservice.book.services.BookService;
import com.backend.app.catalogservice.book.services.GetBookService;
import com.backend.app.shared.error.ErrorMessage;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.models.pagination.DataPagination;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/books")
@CrossOrigin
public class GetBookController {

  private GetBookService getBookService;

  @Autowired
  public GetBookController(GetBookService getBookService) {
    this.getBookService = getBookService;
  }

  @GetMapping("/paged")
  public ResponseEntity<BaseResponse<?>> getBooksPaged(
    @RequestParam(name = "page", required = false) Integer page,
    @RequestParam(name = "limit", required = false) Integer limit,
    @RequestParam(name = "orderBy", required = false) String orderBy,
    @RequestParam(name = "orderName", required = false) String orderName,
    @RequestParam(name = "search", required = false) String search,
    @RequestParam(name = "isActive", required = false) Integer isActive,
    @RequestParam(name = "minPrice", required = false) Integer minPrice,
    @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
    @RequestParam(name = "genres", required = false) List<String> genres
  ) {
    try {
      if (isActive != null && isActive != 0 && isActive != 1) {
        BaseResponse<String> errorResponse = new BaseResponse<String>(4000, "isActive must be 0 or 1", null);
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(errorResponse);
      }

      DataPagination dataPagination = new DataPagination(page, limit, orderBy, orderName, search);
      BaseResponse<?> response = getBookService.getBooksPaged(dataPagination, isActive, minPrice, maxPrice, genres);

      if (response.getCode() != 2000) {
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
      }
      return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> errorResponse = new BaseResponse<String>(5000, error, null);
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
}
