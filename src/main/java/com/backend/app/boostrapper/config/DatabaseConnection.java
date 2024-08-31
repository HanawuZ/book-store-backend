package com.backend.app.boostrapper.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EntityScan("com.backend.app.shared.models.entities")
public class DatabaseConnection {

    @Primary
    @Bean(name = "db1DataSource")
    public DataSource db1DataSource() {
        return DataSourceBuilder
            .create()
            .url("jdbc:mysql://localhost:3306/bookshop?useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true")
            .username("root")
            .password("G5!kT@2y9B#zU8%w")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .type(HikariDataSource.class)
            .build();
    }
}
