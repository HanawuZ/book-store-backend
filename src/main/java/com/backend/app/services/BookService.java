package com.backend.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
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

    private static final String REDIS_KEY_ALL_BOOKS = "books";

    private BookRepository bookRepository;
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, RedisTemplate<Object, Object> redisTemplate) {
        this.bookRepository = bookRepository;
        this.redisTemplate = redisTemplate;
        this.redisTemplate.expire(REDIS_KEY_ALL_BOOKS, 1, TimeUnit.HOURS);
    }

    public List<Book> getAllBooks() {
        try {
            HashOperations<Object, Object, Object> hashOperations = this.redisTemplate.opsForHash();

            Set<Object> keys = this.redisTemplate.keys(REDIS_KEY_ALL_BOOKS + ":*");

            // Get data from hashkey books:*
            if (keys.size() > 0) {
                System.out.println("Get data from redis");
                JSONArray arr = new JSONArray();

                for (Object key : keys) {
                    Map<Object, Object> data = hashOperations.entries(key);

                    arr.put(data);
                }

                // Convert to list
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper.readValue(arr.toString(), new TypeReference<List<Book>>() {
                });

            }

            List<Book> books = this.bookRepository.getAllBooks();
            if (books.isEmpty()) {
                return List.of();
            }

            for (Book book : books) {
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "id", book.getId());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "title", book.getTitle());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "isbn", book.getIsbn());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "title", book.getTitle());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "genre", book.getGenre());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "publicationYear",
                        book.getPublicationYear());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "copiesAvailable",
                        book.getCopiesAvailable());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "price", book.getPrice());
                hashOperations.put(REDIS_KEY_ALL_BOOKS + ":" + book.getId(), "publisher", book.getPublisher());
            }

            return books;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
