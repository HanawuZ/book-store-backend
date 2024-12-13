package com.example.catalog_svc.configs.database;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EntityScan("com.example.catalog_svc.models.entities")
@EnableTransactionManagement
public class DatabaseConnection {

  @Bean(name = "dataSource")
  DataSource dataSource() {
    HikariDataSource dataSource = DataSourceBuilder
        .create()
        .url("jdbc:postgresql://localhost:5432/bookshop-catalog")
        .username("postgres")
        .password("example")
        .driverClassName("org.postgresql.Driver")
        .type(HikariDataSource.class)
        .build();
    dataSource.setMinimumIdle(5);
    dataSource.setMaximumPoolSize(20);
    dataSource.setIdleTimeout(30000); // 30 seconds
    dataSource.setConnectionTestQuery("SELECT 1");

    return dataSource;
  }

  @Bean
  DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }
}
