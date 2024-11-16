package com.backend.app.shared.libraries.csv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.backend.app.catalogservice.book.models.UploadBook;
import com.opencsv.CSVReader;

public class UploadBookCsvHelper extends AbstractCsvHelper {

  public UploadBookCsvHelper() {
    super();
  }

  public UploadBookCsvHelper(List<String> headers) {
    super(headers);
  }

  public List<UploadBook> parseCsv(InputStream inputStream) throws UnsupportedEncodingException, Exception {
    CSVReader csvReader = null;
    try {
      System.out.println("Parsing CSV file...");
      if (this.getHeaders() == null || this.getHeaders().isEmpty()) {
        throw new IllegalStateException("Headers must be initialized before parsing the CSV file.");
      }

      System.out.println("Headers: " + this.getHeaders());
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
      csvReader = new CSVReader(fileReader);

      System.out.println("Reading CSV file...");
      String[] columns = csvReader.readNext();

      System.out.println("CSV file has " + columns.length + " columns.");
      HashMap<Integer, String> indexColumnMap = new HashMap<>();

      // convert all headers to lower case
      // Check if column elements and header elements are same (CSV file has valid
      // headers)
      for (int i = 0; i < columns.length; i++) {
        System.out.println("Column: " + columns[i]);
        String col = columns[i].toLowerCase();

        if (!this.getHeaders().contains(col)) {
          throw new Exception("CSV file has invalid headers. Please check if the headers are correct and try again.");
        }

        indexColumnMap.put(i, columns[i]);
      }

      System.out.println("CSV file has valid headers.");
      System.out.println(indexColumnMap);
      List<UploadBook> uploadBooks = new ArrayList<UploadBook>();

      List<HashMap<String, String>> bookHashMapList = new ArrayList<>();

      String[] values = null;
      while ((values = csvReader.readNext()) != null) {
        HashMap<String, String> hashMapUploadBook = new HashMap<>();
        System.out.println("Reading next row...");
        for (int i = 0; i < values.length; i++) {
          String col = indexColumnMap.get(i);

          System.out.println("Column: " + indexColumnMap.get(i) + ", Value: " + values[i]);
          hashMapUploadBook.put(col, values[i]);

        }

        bookHashMapList.add(hashMapUploadBook);

        // Implement your logic here to set values to your object
        // Ex.
        // If CSV file has columns = ["isbn", "title", "genre", "publicationYear",
        // "copiesAvailable", "price"]
        // values will be ["123456789", "The Great Gatsby", "Fiction", "1925-01-01",
        // "10", "10.99"]

        // Note that column order can be different from the headers
        // Like columns = ["isbn", "title", "copiesAvailable", "price",
        // "publicationYear", "genre"]
        // values will be ["123456789", "The Great Gatsby", "10", "10.99", "1925-01-01",
        // "Fiction"]

      }

      for (HashMap<String, String> hashMapUploadBook : bookHashMapList) {
        UploadBook uploadBook = new UploadBook();
        
        String isbn = String.format("%.0f", Double.parseDouble(hashMapUploadBook.get("isbn")));

        
        // Parse date from format MM/dd/yyyy string into LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate publicationYear = LocalDate.parse(hashMapUploadBook.get("publicationYear"), formatter);
        
        uploadBook.setIsbn(isbn);
        uploadBook.setTitle(hashMapUploadBook.get("title"));
        uploadBook.setGenre(hashMapUploadBook.get("genre"));
        uploadBook.setPublicationYear(publicationYear);
        uploadBook.setCopiesAvailable(Integer.parseInt(hashMapUploadBook.get("copiesAvailable")));
        uploadBook.setPrice(Double.parseDouble(hashMapUploadBook.get("price")));
        uploadBooks.add(uploadBook);
      }

      return uploadBooks;
    } catch (Exception exception) {
      throw exception;
    } finally {
      if (csvReader != null) {
        csvReader.close();
      }
      inputStream.close();
    }
  }

}
