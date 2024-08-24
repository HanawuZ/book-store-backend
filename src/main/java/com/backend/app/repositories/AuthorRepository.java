package com.backend.app.repositories;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import com.backend.app.models.entities.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, String> {
    
    @Query("SELECT a FROM Author a WHERE a.id IN (:authorIds)")
    List<Author> getAuthors(Set<String> authorIds);
}

