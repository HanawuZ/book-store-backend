package com.backend.app.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.sql.Timestamp;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.app.models.entities.Author;
import com.backend.app.models.entities.Book;
import com.backend.app.models.entities.Publisher;
import com.backend.app.models.requests.BookRequest;
import com.backend.app.repositories.AuthorRepository;
import com.backend.app.repositories.BookRepository;
import com.backend.app.repositories.PublisherRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.backend.app.utils.redis.RedisHashUtil;

@Service
public class BookService {

    private static final String REDIS_KEY_PREFIX_BOOKS = "books";
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;
    private AuthorRepository authorRepository;
    private RedisHashUtil redisHashUtil;

    @Autowired
    public BookService(BookRepository bookRepository, RedisTemplate<Object, Object> redisTemplate,
            PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.redisHashUtil = new RedisHashUtil(redisTemplate).setKeyPrefix(REDIS_KEY_PREFIX_BOOKS);
    }

    public List<Book> getAllBooks() {
        try {

            JSONArray booksFromCache = this.redisHashUtil.getHashValues(REDIS_KEY_PREFIX_BOOKS + ":*");
            if (booksFromCache != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper.readValue(booksFromCache.toString(), new TypeReference<List<Book>>() {
                });

            }

            List<Book> books = this.bookRepository.getAllBooks();
            if (books.isEmpty()) {
                return List.of();
            }

            this.redisHashUtil.put(books, Long.valueOf(60), TimeUnit.MINUTES);
            return books;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Optional<Book> getBookById(String id) {
        try {
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public Optional<Book> createBook(BookRequest bookRequest) {
        try {

            LocalDateTime currentDateTime = LocalDateTime.now();

            // Format the date and time
            String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Parse the formatted date and time back to LocalDateTime
            LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Convert to Timestamp
            Timestamp now = Timestamp.valueOf(parsedDateTime);

            Book book = new Book();
            book.setId(UUID.randomUUID().toString());
            book.setIsbn(bookRequest.getIsbn());
            book.setTitle(bookRequest.getTitle());
            book.setGenre(bookRequest.getGenre());
            book.setCopiesAvailable(bookRequest.getCopiesAvailable());
            book.setPrice(bookRequest.getPrice());
            book.setPublicationYear(bookRequest.getPublicationYear());
            book.setCreatedDate(now);
            book.setCreatedBy("system");
            book.setUpdatedDate(now);
            book.setUpdatedBy("system");

            Optional<Publisher> publisher = this.publisherRepository.getPublisherById(bookRequest.getPublisherId());
            if (!publisher.isEmpty()) {
                // Get value from publisher
                Publisher publisherData = publisher.get();
                book.setPublisher(publisherData);
            }

            List<Author> authors = this.authorRepository.getAuthors(bookRequest.getAuthorIds());
            if (!authors.isEmpty()) {
                Set<Author> authorsSet = Set.copyOf(authors);
                book.setAuthors(authorsSet);
            }

            Optional<Book> newBook = this.bookRepository.createBook(book);
            if (newBook.isEmpty()) {
                return Optional.empty();
            }

            return newBook;
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
