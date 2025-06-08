package com.ex.route_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Сервис для взаимодействия с Redis.
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Сохраняет объект в Redis без времени жизни.
     *
     * @param key   ключ, по которому сохраняется объект
     * @param value сохраняемое значение
     * @param <T>   тип значения
     */
    public <T> void save(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Сохраняет объект в Redis с указанным временем жизни.
     *
     * @param key   ключ, по которому сохраняется объект
     * @param value сохраняемое значение
     * @param ttl   время жизни записи
     * @param <T>   тип значения
     */
    public <T> void save(String key, T value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * Извлекает объект из Redis и конвертирует его в указанный класс.
     *
     * @param key   ключ, по которому хранится объект
     * @param clazz целевой класс для преобразования
     * @param <T>   тип возвращаемого объекта
     * @return объект из Redis или {@code null}, если по ключу ничего не найдено
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        // конвертируем LinkedHashMap в нужный класс
        return objectMapper.convertValue(value, clazz);
    }

    /**
     * Удаляет запись из Redis по ключу.
     *
     * @param key ключ удаляемой записи
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
