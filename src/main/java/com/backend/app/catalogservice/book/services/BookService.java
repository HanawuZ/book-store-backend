package com.backend.app.catalogservice.book.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.app.shared.libraries.csv.UploadBookCsvHelper;
import com.backend.app.shared.libraries.http.BaseResponse;

@Service
public class BookService {
  
  private UploadBookCsvHelper uploadBookCsvHelper;
  
  @Autowired
  public BookService(UploadBookCsvHelper uploadBookCsvHelper) {
    this.uploadBookCsvHelper = uploadBookCsvHelper;
  }

  public BaseResponse<String> createBookFromUpload(MultipartFile file) throws Exception {
    try {

      if (!uploadBookCsvHelper.hasCsvFormat(file)) {
        return new BaseResponse<String>(5000, "File is not in CSV format", null);
      }

      uploadBookCsvHelper.parseCsv(file.getInputStream());      
      return null;

    } catch (Exception exception) {
      throw exception;
    }
  }
}
