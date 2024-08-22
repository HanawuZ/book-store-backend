package com.backend.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.app.models.entities.Book;
import com.backend.app.models.requests.BookRequest;
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

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("");

        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookRequest bookRequest) {
        try {
            Optional<Book> book = this.bookService.createBook(bookRequest);
            if (book.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Failed to create book");
            }
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body("Book created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
