package com.backend.app.catalogservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.models.pagination.DataPagination;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

  @Autowired
  public BookController() {

  }

  @GetMapping("/paged")
  public ResponseEntity<BaseResponse<?>> getPagedBooks(
      @RequestParam("page") Integer page,
      @RequestParam("limit") Integer limit,
      @RequestParam("search") String search,
      @RequestParam("orderBy") String orderBy,
      @RequestParam("orderName") String orderName) {
    try {
      DataPagination pagination = new DataPagination(page, limit, orderBy, orderName, search, null);
      return null;
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = String.format("Internal server error: %s", exception.getMessage());
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
          .body(new BaseResponse<>(5000, error, null));

    }
  }

  @PostMapping
  public void createBook() {

  }
}
