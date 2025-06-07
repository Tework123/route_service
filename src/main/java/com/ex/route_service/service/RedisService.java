package com.ex.route_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public <T> void save(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void save(String key, T value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        // конвертируем LinkedHashMap в нужный класс
        return objectMapper.convertValue(value, clazz);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
