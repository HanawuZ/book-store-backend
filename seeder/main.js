// mockData.js
import pkg from "pg";
import { faker } from "@faker-js/faker";

const { Client } = pkg;

// === CONFIGURATION ===
const client = new Client({
  host: "localhost",
  port: 5433,
  database: "bookshop-catalog",
  user: "user1",
  password: "example1",
});

async function seedDatabase() {
  try {
    await client.connect();

    // Clear existing data (optional)
    await client.query("DELETE FROM book_authors");
    await client.query("DELETE FROM books");
    await client.query("DELETE FROM authors");
    await client.query("DELETE FROM publishers");

    // Insert publishers
    const publisherIds = [];
    for (let i = 0; i < 10; i++) {
      const id = faker.string.uuid();
      publisherIds.push(id);
      await client.query(
        `INSERT INTO publishers (id, address, created_by, created_date, email, name, phone_number, updated_by, updated_date)
         VALUES ($1, $2, $3, $4, $5, $6, $7, NULL, NULL)`,
        [
          id,
          faker.location.streetAddress(),
          "system",
          faker.date.past(),
          faker.internet.email(),
          faker.book.publisher(),
          faker.phone.number(),
        ]
      );
    }

    // Insert authors
    const authorIds = [];
    for (let i = 0; i < 30; i++) {
      const id = faker.string.uuid();
      authorIds.push(id);
      await client.query(
        `INSERT INTO authors (id, created_by, created_date, dob, firstname, lastname, pseudonym, updated_by, updated_date)
         VALUES ($1, $2, $3, $4, $5, $6, $7, NULL, NULL)`,
        [
          id,
          "system",
          faker.date.past(),
          faker.date.birthdate({ min: 25, max: 80, mode: "age" }),
          faker.person.firstName(),
          faker.person.lastName(),
          faker.datatype.boolean()
            ? faker.person.firstName() + " " + faker.word.noun()
            : null,
        ]
      );
    }

    // Insert books

    // Insert books with distinct titles
    const bookIds = [];
    const usedTitles = new Set();

    for (let i = 0; i < 500; i++) {
      let title;
      do {
        title = `${faker.commerce.productAdjective()} ${faker.word.noun()} ${faker.helpers.arrayElement(
          ["Chronicles", "Saga", "Tales", "Adventures"]
        )}`;
      } while (usedTitles.has(title));

      usedTitles.add(title);

      const id = faker.string.uuid();
      bookIds.push(id);

      await client.query(
        `INSERT INTO public.books (id, copies_available, created_by, created_date, genre, is_active, isbn, price, publication_year, title, updated_by, updated_date, publisher_id)
     VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, NULL, NULL, $11)`,
        [
          id,
          faker.number.int({ min: 1, max: 20 }),
          "system",
          faker.date.past(),
          faker.helpers.arrayElement([
            "Fiction",
            "Non-fiction",
            "Sci-Fi",
            "Romance",
            "Fantasy",
          ]),
          true,
          faker.commerce.isbn(),
          parseFloat(faker.commerce.price({ min: 5, max: 50 })),
          faker.date.past({ years: 20 }),
          title,
          faker.helpers.arrayElement(publisherIds),
        ]
      );
    }

    // const bookIds = [];
    // for (let i = 0; i < 500; i++) {
    //   const id = faker.string.uuid();
    //   bookIds.push(id);
    //   await client.query(
    //     `INSERT INTO books (id, copies_available, created_by, created_date, genre, is_active, isbn, price, publication_year, title, updated_by, updated_date, publisher_id)
    //      VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, NULL, NULL, $11)`,
    //     [
    //       id,
    //       faker.number.int({ min: 1, max: 20 }),
    //       'system',
    //       faker.date.past(),
    //       faker.helpers.arrayElement(['Fiction', 'Non-fiction', 'Sci-Fi', 'Romance', 'Fantasy']),
    //       true,
    //       faker.commerce.isbn(),
    //       parseFloat(faker.commerce.price({ min: 5, max: 50 })),
    //       faker.date.past({ years: 20 }),
    //       faker.book.title(),
    //       faker.helpers.arrayElement(publisherIds),
    //     ]
    //   );
    // }

    // Insert book_authors (random associations)
    for (const bookId of bookIds) {
      const authorsForBook = faker.helpers.arrayElements(
        authorIds,
        faker.number.int({ min: 1, max: 3 })
      );
      for (const authorId of authorsForBook) {
        await client.query(
          `INSERT INTO book_authors (book_id, author_id) VALUES ($1, $2)`,
          [bookId, authorId]
        );
      }
    }

    console.log("✅ Mock data inserted successfully");
  } catch (err) {
    console.error("❌ Error seeding database:", err);
  } finally {
    await client.end();
  }
}

seedDatabase();
