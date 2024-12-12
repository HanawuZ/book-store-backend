package com.backend.app.catalogservice.book.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.app.catalogservice.book.services.BookService;
import com.backend.app.shared.error.ErrorMessage;
import com.backend.app.shared.libraries.http.BaseResponse;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/v1/books")
@CrossOrigin
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping("/upload")
  public ResponseEntity<BaseResponse<String>> upload(@RequestParam("file") MultipartFile file) {
    try {
      BaseResponse<String> response = bookService.createBookFromUpload(file);
      if (response.getCode() != 2001) {
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
      }
      return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> errorResponse = new BaseResponse<String>(5000, error, null);
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }

}
