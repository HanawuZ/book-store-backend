package com.backend.app.catalogservice.book.services;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.app.shared.libraries.csv.UploadBookCsvHelper;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.catalogservice.book.repositories.BookRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class BookService {

  private UploadBookCsvHelper uploadBookCsvHelper;
  private BookRepository bookRepository;
  private JobLauncher jobLauncher;
  private Job job;

  @Autowired
  public BookService(
      UploadBookCsvHelper uploadBookCsvHelper,
      BookRepository bookRepository,
      JobLauncher jobLauncher,
      Job job) {
    this.uploadBookCsvHelper = uploadBookCsvHelper;
    this.bookRepository = bookRepository;
    this.jobLauncher = jobLauncher;
    this.job = job;
  }

  public BaseResponse<String> createBookFromUpload(MultipartFile file) throws Exception {
    try {

      // Check if file is exist
      if (file.isEmpty()) {
        return new BaseResponse<String>(4000, "File is empty", null);
      }

      if (!uploadBookCsvHelper.hasCsvFormat(file)) {
        return new BaseResponse<String>(4000, "File is not in CSV format", null);
      }

      // Save file to the local file system
      Path path = Paths.get("uploads/" + file.getOriginalFilename());
      Files.createDirectories(path.getParent());
      Files.write(path, file.getBytes());

      // Launch the batch job
      JobParameters jobParameters = new JobParametersBuilder()
          .addLong("time", System.currentTimeMillis()) // Ensure unique parameters
          .toJobParameters();

      JobExecution jobExecution = jobLauncher.run(job, jobParameters);
      BatchStatus jobStatus = jobExecution.getStatus();

      if (jobStatus.isUnsuccessful()) {
        return new BaseResponse<String>(4000, "Error in batch job", null);
      }

      return new BaseResponse<String>(2001, "Success", null);

    } catch (Exception exception) {
      throw exception;
    }
  }
}
