package com.backend.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Object> getAllBooks() {
        try {
            List<Book> books = this.bookService.getAllBooks();
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("No books found");
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(books);

        } catch (Exception e) {
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable String id) {
        try {
            if (id == null) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Book id is required");
            }

            Optional<Book> book = this.bookService.getBookById(id);
            if (book.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Book not found");
            }

            return ResponseEntity.status(HttpServletResponse.SC_OK).body(Optional.of(book.get()));

        } catch (Exception e) {
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody BookRequest bookRequest) {
        try {
            Optional<Book> book = this.bookService.createBook(bookRequest);
            if (book.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Failed to create book");
            }
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body("Book created successfully");
        } catch (Exception e) {
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateBook(@RequestBody BookRequest bookRequest) {
        try {
            if (bookRequest.getId() == null || bookRequest.getId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Book id is required");
            }

            Optional<Book> book = this.bookService.updateBook(bookRequest);
            if (book.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Book not found");
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("Book updated successfully");
        } catch (Exception e) {
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable String id) {
        try {
            if (id == null || id.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Book id is required");
            }

            Boolean success = this.bookService.deleteBook(id);
            if (!success) {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Cannot delete book");
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("Book deleted successfully");
        } catch (Exception e) {
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
