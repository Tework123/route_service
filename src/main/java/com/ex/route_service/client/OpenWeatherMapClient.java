package com.ex.route_service.client;

import com.ex.route_service.dto.OpenWeatherMapDto.OpenWeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Клиент для получения данных о погоде с OpenWeatherMap API.
 */
@Component
@RequiredArgsConstructor
public class OpenWeatherMapClient {
    private final RestTemplate restTemplate;
    private final RequestBuilder requestBuilder;

    @Value("${openweathermap.api-key}")
    private String API_KEY;

    /**
     * Получить текущую погоду по координатам (долгота, широта).
     * Используется метрическая система измерения (Цельсий).
     *
     * @param longitude долгота
     * @param latitude широта
     * @return DTO с данными о погоде
     */
    public OpenWeatherResponseDto getWeather(Double longitude, Double latitude){

        Map<String, String> params = Map.of(
                "lon", String.valueOf(longitude),
                "lat", String.valueOf(latitude),
                "units", "metric",
                "appid", API_KEY
        );

        String url = requestBuilder.buildUrl(
                "https",
                "api.openweathermap.org",
                "/data/2.5/weather",
                null,
                params
        );

        return restTemplate.getForObject(url, OpenWeatherResponseDto.class);
    }

}
