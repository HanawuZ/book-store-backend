package com.backend.app.shared.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "books", indexes = @Index(columnList = "isbn"))
public class Book implements Serializable {
    
    @Id
    @Column(name = "id")
    private String id;

    @Column(unique = true, name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "publication_year")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate publicationYear;

    @Column(name = "copies_available")
    private Integer copiesAvailable;

    @Column(name = "price")
    private Double price;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "book_authors", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @JsonProperty("isActive")
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @JsonProperty("createdDate")
    @Column(name = "created_date")
    private Date createdDate;

    @JsonProperty("createdBy")
    @Column(name = "created_by")
    private String createdBy;

    @JsonProperty("updatedDate")
    @Column(name = "updated_date")
    private Date updatedDate;

    @JsonProperty("updatedBy")
    @Column(name = "updated_by")
    private String updatedBy;
}
