package com.backend.app.shared.libraries.redis;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

public class MockRedisValueUtility extends RedisValueUtility {
    
    private HashMap<String, String> cache = new HashMap<>();

    public MockRedisValueUtility() {
        super(null);
    }

    @Override
    public String getValue(String key) {
        return cache.get(key);
    }

    @Override
    public void setValue(String key, String value, Integer duration, TimeUnit timeUnit) {
        cache.put(key, value);
    }
}
