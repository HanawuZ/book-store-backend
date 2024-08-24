package com.backend.app.utils.redis;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.json.JSONArray;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisHashUtil {

    private final RedisTemplate<Object, Object> redisTemplate;
    private final HashOperations<Object, Object, Object> hashOperations;

    public RedisHashUtil() {
        this(new RedisTemplate<>());
    }

    public RedisHashUtil(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    
    public JSONArray get(String hashKey) {
        Set<Object> keys = this.redisTemplate.keys(hashKey);

        JSONArray arr = new JSONArray();

        for (Object key : keys) {
            Map<Object, Object> data = this.hashOperations.entries(key);
            arr.put(data);
        }

        return arr;
    }

    public <T> Boolean put(String keyPrefix, Object data, Long timeToLive, TimeUnit timeUnit) {
        try {
            if (data instanceof Collection) {
                Collection<T> collectionData = (Collection<T>) data;
                collectionData.forEach(object ->  storeInRedis(keyPrefix, object, timeToLive, timeUnit));
            } else {
                storeInRedis(keyPrefix, data, timeToLive, timeUnit);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean delete(String hashKey) {
        return this.redisTemplate.delete(hashKey);
    }
    
    private void storeInRedis(String keyPrefix, Object data, Long timeToLive, TimeUnit timeUnit) {
        Class<?> className = data.getClass();
        String hashKey = keyPrefix + ":" + getObjectId(data);        
        
        Field[] fields = className.getDeclaredFields();

        Arrays.stream(fields).forEach(field -> {
            String fieldName = field.getName();
            Object value = getValue(data, fieldName);
            this.hashOperations.put(hashKey, fieldName, value);
        });

        this.redisTemplate.expire(hashKey, timeToLive, timeUnit);
    }

    private static <T> String getObjectId(T object) {
        try {
            Method getId = object.getClass().getDeclaredMethod("getId");
            return (String) getId.invoke(object, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Object getValue(T object, String fieldName) {
        try {
            String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            Method getterMethod = object.getClass().getDeclaredMethod(getterMethodName);

            return getterMethod.invoke(object);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
