package com.backend.app.boostrapper.config.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.backend.app.shared.libraries.csv.UploadBookCsvHelper;

import org.springframework.context.annotation.Configuration;

@Configuration  
public class CsvHelperConfiguration {

  @Bean
  public UploadBookCsvHelper uploadBookCsvHelper() {
    List<String> headers = new ArrayList<>(List.of("isbn", "title", "genre", "publicationYear", "copiesAvailable", "price"));
    return new UploadBookCsvHelper(headers);
  }
}
