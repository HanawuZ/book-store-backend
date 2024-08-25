package com.backend.app.repositories;

import java.util.List;
import java.util.Optional;

import com.backend.app.models.entities.Book;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    
    List<Book> findAll();

    Optional<Book> getBookById(String id);

    Book save(Book book);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book b WHERE b.id = :id")
    void deleteBookById(@Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM book_authors WHERE book_id = :id", nativeQuery = true)
    void deleteBookAuthorsByBookId(@Param("id") String id);

    @Transactional
    default void deleteBookByIdWithAuthors(String id) {
        deleteBookAuthorsByBookId(id);
        deleteBookById(id);
    }
}
