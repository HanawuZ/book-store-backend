package com.backend.app.shared.libraries.redis;

import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

public class RedisValueUtility {

  private RedisTemplate<Object, Object> redisTemplate;

  public RedisValueUtility(RedisTemplate<Object, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public String getValue(String key) {
    return (String) this.redisTemplate.opsForValue().get(key);
  }

  public void setValue(String key, String value, Integer duration, TimeUnit timeUnit) {
    this.redisTemplate.opsForValue().set(key, value);
    this.redisTemplate.expire(key, duration, timeUnit);
  }
}
