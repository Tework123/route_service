package com.ex.route_service.client;

import com.ex.route_service.dto.OpenWeatherMapDto.OpenWeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpenWeatherMapClient {
    private final RestTemplate restTemplate;
    private final RequestBuilder requestBuilder;

    private static final String API_KEY = "aa0a32f4341403aa09700231c23386ce";

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
