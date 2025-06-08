package com.ex.route_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для настройки Jackson ObjectMapper.
 */
@Configuration
public class JacksonConfig {

    /**
     * Создает и настраивает бин ObjectMapper с поддержкой Java 8 Date/Time API.
     * Отключает запись дат в виде timestamp, используя читаемый формат ISO-8601.
     *
     * @return настроенный экземпляр ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}