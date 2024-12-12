package com.backend.app.boostrapper.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.backend.app.catalogservice.book.models.UploadBook;
import com.backend.app.shared.models.entities.Book;

import javax.sql.DataSource;

import org.springframework.batch.core.job.builder.JobBuilder;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
  @Bean
  public FlatFileItemReader<UploadBook> reader() throws Exception {
    System.out.println("READER");
    return new FlatFileItemReaderBuilder<UploadBook>()
        .name("bookItemReader")
        .resource(new FileSystemResource("uploads/samplecsv.csv"))
        .targetType(UploadBook.class)
        .linesToSkip(1)
        .delimited()
        .delimiter(",")
        .names("isbn", "title", "genre", "publicationYear", "copiesAvailable", "price")
        .build();
  }

  @Bean
  public BookItemProcessor processor() {
    return new BookItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<Book> writer(DataSource dataSource) throws Exception {

    String insertQuery = """
        INSERT INTO books (
          id, copies_available, created_by, created_date, genre, is_active, isbn, price, publication_year, title, updated_by, updated_date
        )
        VALUES (
          :id, :copiesAvailable, :createdBy, :createdDate, :genre, :isActive, :isbn, :price, :publicationYear, :title, :updatedBy, :updatedDate
        )
        """;

    System.out.println("WRITER");
    return new JdbcBatchItemWriterBuilder<Book>()
        .sql(insertQuery)
        .dataSource(dataSource)
        .beanMapped()
        .build();
  }

  @Bean
  public Step step1(
      JobRepository jobRepository,
      DataSourceTransactionManager transactionManager,
      FlatFileItemReader<UploadBook> reader,
      BookItemProcessor processor,
      JdbcBatchItemWriter<Book> writer) throws Exception {
    System.out.println("STEP1");
    return new StepBuilder("step1", jobRepository)
        .<UploadBook, Book>chunk(3, transactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  @Bean
  public Job job(JobRepository jobRepository, Step step1) throws Exception {
    System.out.println("DOING JOB");
    return new JobBuilder("importBookJob", jobRepository)
        .start(step1)
        .build();
  }
}
