package com.backend.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.app.models.entities.Book;
import com.backend.app.services.BookService;

import jakarta.servlet.http.HttpServletResponse;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/books")
public class BookController {
    
    private BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            if (books.isEmpty()) {
                // Send bad request and message
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("No books found");
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(books);

        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

}
