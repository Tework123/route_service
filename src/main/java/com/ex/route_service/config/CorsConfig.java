package com.ex.route_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфиг для настройки спецификации CORS.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Настраивает шаблоны URL для CORS.
     * Параметр "/**" разрешает использовать CORS по всему приложению.
     *
     * @param registry экземпляр CorsRegistry - помогает регистрировать глобальные сопоставления CorsConfiguration
     *                 на основе шаблонов URL.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("*");
    }
}