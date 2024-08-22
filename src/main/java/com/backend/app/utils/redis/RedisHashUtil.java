package com.backend.app.utils.redis;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RedisHashUtil {

    private RedisTemplate<Object, Object> redisTemplate;
    private String keyPrefix = "";
    private HashOperations<Object, Object, Object> hashOperations;

    public RedisHashUtil() {
        this.redisTemplate = new RedisTemplate<>();
        hashOperations =  this.redisTemplate.opsForHash();
    }

    public RedisHashUtil(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = this.redisTemplate.opsForHash();
    }

    public RedisHashUtil setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
        return this;
    }

    public <T> Boolean put(Object data, Long timeToLive, TimeUnit timeUnit) {
        try {
            if (data instanceof Collection) {
                Collection<T> collectionData = (Collection<T>) data;
                collectionData.forEach(object ->  storeInRedis(object, timeToLive, timeUnit));
            } else {
                storeInRedis(data, timeToLive, timeUnit);
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public JSONArray getHashValues(String hashKey) {
        Set<Object> keys = this.redisTemplate.keys(hashKey);

        JSONArray arr = new JSONArray();

        for (Object key : keys) {
            Map<Object, Object> data = this.hashOperations.entries(key);
            arr.put(data);
        }

        return arr;
    }
    
    private void storeInRedis(Object data, Long timeToLive, TimeUnit timeUnit) {
        Class<?> className = data.getClass();
        String hashKey = this.keyPrefix + ":" + getObjectId(data);

        // I want secondary index on field  title
        
        
        Field[] fields = className.getDeclaredFields();

        Arrays.stream(fields).forEach(field -> {
            String fieldName = field.getName();
            Object value = getValue(data, fieldName);
            hashOperations.put(hashKey, fieldName, value);

        });

        this.redisTemplate.expire(hashKey, timeToLive, timeUnit);

    }

    private static <T> String getObjectId(T object) {
        try {
            Method getId = object.getClass().getDeclaredMethod("getId");
            
            return (String) getId.invoke(object, null);
        } catch (Exception e) {
            e.printStackTrace();
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
