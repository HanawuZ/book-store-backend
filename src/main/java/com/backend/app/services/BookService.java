package com.backend.app.services;

import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.app.models.entities.Book;
import com.backend.app.repositories.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;

@Service
public class BookService {

    private static final String REDIS_KEY_ALL_BOOKS = "BOOKS";

    private BookRepository bookRepository;
    private RedisTemplate<Object, Object> redisTemplate;
    ObjectMapper objectMapper;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public BookService(BookRepository bookRepository, RedisTemplate<Object, Object> redisTemplate) {
        this.bookRepository = bookRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();

         // Register the JSR310 module for Java 8 date/time types
        objectMapper.registerModule(new JavaTimeModule());

        // To ensure serialization works properly for date/time, you may also need this
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Set date format for non-Java 8 date/time types
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(simpleDateFormat);


    }

    public List<Book> getAllBooks() {
        try {
            Object cachedBooks = this.redisTemplate.opsForValue().get(REDIS_KEY_ALL_BOOKS);
            System.out.println(cachedBooks);
            if (cachedBooks != null || !cachedBooks.toString().isEmpty()) {
                // Convert json array to list
                JSONArray jsonArray = new JSONArray(cachedBooks.toString());
                List<Book> books = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<Book>>() {});
                return books;
                
            }

            List<Book> books = this.bookRepository.getAllBooks();
            if (!books.isEmpty()) {

                String booksJsonString = objectMapper.writeValueAsString(books);  
                this.redisTemplate.opsForValue().set(REDIS_KEY_ALL_BOOKS, booksJsonString);
            }
            return books;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
