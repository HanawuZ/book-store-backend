package com.backend.app.boostrapper.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.backend.app.shared.libraries.redis.RedisValueUtility;

@Configuration
public class RedisUtilities {

  private RedisTemplate<Object, Object> redisTemplate;

  @Autowired
  public RedisUtilities(RedisTemplate<Object, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }
  
  @Bean
  public RedisValueUtility redisValueUtility() {
    return new RedisValueUtility(redisTemplate);
  }
}
