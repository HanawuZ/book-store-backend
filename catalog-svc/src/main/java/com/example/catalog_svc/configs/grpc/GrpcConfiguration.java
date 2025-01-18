package com.example.catalog_svc.configs.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.catalog_svc.app.grpc.health.pingpong.PingPongImpl;


@Configuration
public class GrpcConfiguration {
  
  @Bean
  PingPongImpl pingPongImpl() {
    return new PingPongImpl();
  }
}
