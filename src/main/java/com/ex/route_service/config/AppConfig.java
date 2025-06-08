package com.ex.route_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Конфигурационный класс для создания бинов Spring.
 */
@Configuration
public class AppConfig {

    /**
     * Создает и настраивает бин RestTemplate для выполнения HTTP-запросов.
     *
     * @return экземпляр RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}