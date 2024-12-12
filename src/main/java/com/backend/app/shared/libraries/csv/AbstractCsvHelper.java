package com.backend.app.shared.libraries.csv;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractCsvHelper {
  private static String FILE_TYPE_CSV = "text/csv";
  private List<String> headers = new ArrayList<>();

  public AbstractCsvHelper() {
  }

  public AbstractCsvHelper(List<String> headers) {
    System.out.println("Headers: " + headers);
    System.out.println("INITIALIZE CSV HELPER");
    // convert all headers to lower case
    for (int i = 0; i < headers.size(); i++) {
      headers.set(i, headers.get(i).toLowerCase());
    }

    this.headers = headers;
  }

  public Boolean hasCsvFormat(MultipartFile file) {
    return FILE_TYPE_CSV.equals(file.getContentType());
  }

  public void setHeaders(List<String> headers) {

    // convert all headers to lower case
    for (int i = 0; i < headers.size(); i++) {
      headers.set(i, headers.get(i).toLowerCase());
    }

    this.headers = headers;
  }

  public List<String> getHeaders() {
    return this.headers;
  }
}
