package com.backend.app.catalogservice.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.Data;

public class ShoppingCartModel {
    
    @Data
    public static class ShoppingCartResponse {
        @JsonProperty("totalPrice")
        private Double totalPrice;

        @JsonProperty("items")
        private List<CartItem> items;
    }

    @Data
    public static class CartQueryResult {

        public CartQueryResult(String bookId, String title, String genre, Double price, Integer quantity) {
            this.bookId = bookId;
            this.title = title;
            this.genre = genre;
            this.price = price;
            this.quantity = quantity;
        }

        @JsonProperty("bookId")
        @Column(name = "book_id")
        private String bookId;

        @JsonProperty("title")
        @Column(name = "title")
        private String title;

        @JsonProperty("genre")
        @Column(name = "genre")
        private String genre;

        @JsonProperty("price")
        @Column(name = "price")
        private Double price;

        @JsonProperty("quantity")
        @Column(name = "quantity")
        private Integer quantity;
    }

    @Data
    public static class CartItem {
        @JsonProperty("bookId")
        private String bookId;

        @JsonProperty("title")
        private String title;

        @JsonProperty("quantity")
        private Integer quantity;

        @JsonProperty("genre")
        private String genre;

        @JsonProperty("price")
        private Double price;
    }

}

