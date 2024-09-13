package com.backend.app.catalogservicetest.services.cart;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.backend.app.catalogservice.repositories.CartRepository;
import com.backend.app.catalogservice.services.ShoppingCartService;
import com.backend.app.shared.models.entities.Book;
import com.backend.app.shared.models.entities.Cart;
import com.backend.app.shared.models.entities.User;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@SpringBootTest
@Import(ShoppingCartServiceTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class ShoppingCartServiceTest {
    
    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @Autowired
    private CartRepository cartRepository;

    @BeforeAll
    public void setup() {

        // User user1 = new User();
        // user1.setId("user1");
        // user1.setUsername("user1");
        // user1.setEmail("a@gmail.com");
        // // user1.setFirstName("John");
        // // user1.setLastName("Doe");
        // user1.setProfilePicture("https://example.com/image.jpg");
        // user1.setCreatedDate(new Date());
        // user1.setPassword("123456");
        // user1.setIsUsing2FA(false);

        // Book book1 = new Book();
        // book1.setId("book1");
        // book1.setTitle("Book 1");
        // book1.setGenre("Genre 1");
        // book1.setIsbn("1234567890");
        // book1.setCopiesAvailable(10);

        // Cart cart1 = new Cart();
        // cart1.setBook(book1);
        // cart1.setCustomer(user1);
        // cart1.setQuantity(10);
    }
}
