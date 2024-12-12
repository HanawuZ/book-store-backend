# Book store backend application

## Book API

- [x] Bulk Upload
  Challenge: Implement bulk upload of books using CSV/Excel files. Validate the data before adding it to the database.
  - [x] Use Apache POI or OpenCSV for parsing.
  - [x] Spring Batch for processing.
  - [x] Return error response if job execution is failed.

- [] Book Reviews and Ratings
  Challenge: Allow users to leave reviews and ratings for books. Implement an average rating system and allow filtering books by ratings.
  Enhancement: Add spam detection for reviews using a basic AI model or a third-party service.

- [] Order and Payment Workflow
  Challenge: Create a full order and payment workflow, integrating with a payment gateway (e.g., Stripe or PayPal). Include support for discounts, gift cards, and coupons.
  Enhancement: Implement asynchronous order processing using RabbitMQ or Kafka.

- [] Wishlist and Cart
  Challenge: Implement a cart system where users can add/remove items. Include functionality to save wishlists for future purchases.
  Enhancement: Make the cart persistent by linking it to the user’s session or account.

- [] Internationalization
  Challenge: Support multiple languages and currencies for a global audience. Implement currency conversion using a live exchange rate API.
  Enhancement: Allow users to switch languages dynamically and persist their preference.

- [] Event-Driven Architecture
  Challenge: Use an event-driven architecture to handle operations like sending emails when orders are placed or inventory is low.
  Enhancement: Leverage tools like Kafka, RabbitMQ, or AWS SNS for event management.

- [?] Advanced Search and Filtering
      Challenge: Implement a search API that allows users to filter books by multiple criteria like title, author, genre, price range, publication year, and availability. Support complex queries, such as searching for books by multiple authors or genres.
  - [x] Use normal pagination including 'Page', 'Page size', 'Order by' & 'Order name'.
  - [x] Can 'Search' by title, isbn, genre & publisher's name
  - [x] Add more complex filter including price range, multiple genres and availability.
  - [ ] Use Elasticsearch or RediSearch for partial matches and typos.

- [] Integration with External APIs
  Challenge: Fetch book metadata from external APIs like Google Books or OpenLibrary to auto-populate fields.
  Enhancement: Implement caching using Redis for frequently accessed data.

- [x] Get books paged 
- [ ] Get book by id
- [ ] Create book
- [ ] Update book
- [ ] Delete book

## Author API

- [ ] Get author paged
- [ ] Get author detail
- [ ] Create author
- [ ] Update author
- [ ] Delete author