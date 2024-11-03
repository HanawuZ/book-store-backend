package com.backend.app.orderservice.models.query;

import java.sql.Date;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CartItemQueryResult {

    public CartItemQueryResult(
        String productId,
        String genre, 
        String isbn, 
        String title, 
        Integer quantity, 
        Double price, 
        Date publicationYear, 
        String publisherName,
        String customerFirstname, 
        String customerLastname, 
        String customerPhoneOne, 
        String customerPhoneTwo
    ) {
        this.productId = productId;
        this.genre = genre;
        this.isbn = isbn;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.publicationYear = publicationYear;
        this.publisherName = publisherName;
        this.customerFirstname = customerFirstname;
        this.customerLastname = customerLastname;
        this.customerPhoneOne = customerPhoneOne;
        this.customerPhoneTwo = customerPhoneTwo;
    }
    @Column(name = "product_id")
    private String productId;

    @Column(name = "genre")
    private String genre;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "publication_year")
    private Date publicationYear;

    @Column(name = "customer_firstname")
    private String customerFirstname;

    @Column(name = "customer_lastname")
    private String customerLastname;

    @Column(name = "customer_phone_one")
    private String customerPhoneOne;

    @Column(name = "customer_phone_two")
    private String customerPhoneTwo;

    @Column(name = "publisher_name")
    private String publisherName;
}
