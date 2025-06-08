package com.ex.route_service.client;

import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.dto.OpenRouteServiceDto.PostRouteResponseDto;
import com.ex.route_service.enums.TransportType;
import com.ex.route_service.mapper.OpenRouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Клиент для взаимодействия с OpenRouteService API.
 * Используется для получения маршрутов по координатам и типу транспорта.
 */
@Component
@RequiredArgsConstructor
public class OpenRouteClient {

    private final RestTemplate restTemplate;

    private final RequestBuilder requestBuilder;

    @Value("${openrouteservice.api-key}")
    private String API_KEY;

    /**
     * Получить маршрут между двумя точками (курьер и клиент) с заданным типом транспорта.
     *
     * @param longitudeCourier долгота курьера
     * @param latitudeCourier широта курьера
     * @param longitudeClient долгота клиента
     * @param latitudeClient широта клиента
     * @param transportType тип транспорта (например, driving-car, cycling, foot)
     * @return DTO с информацией о маршруте
     */
    public GetRouteResponseDto getRoute(Double longitudeCourier, Double latitudeCourier,
                                        Double longitudeClient, Double latitudeClient,
                                        TransportType transportType) {
        Map<String, String> params = Map.of(
                "api_key", API_KEY,
                "start", longitudeCourier + "," + latitudeCourier,
                "end", longitudeClient + "," + latitudeClient
        );
        String url = requestBuilder.buildUrl(
                "https",
                "api.openrouteservice.org",
                "/v2/directions/",
                transportType.getOpenRouteName(),
                params
        );
        return restTemplate.getForObject(url, GetRouteResponseDto.class);
    }

    /**
     * Получить маршрут по списку координат и типу транспорта.
     *
     * @param coordinates список координат маршрута (каждая точка — список из [долгота, широта])
     * @param transportType тип транспорта
     * @return DTO с информацией о маршруте
     */
    public GetRouteResponseDto getRoute(List<List<Double>> coordinates, TransportType transportType) {
        String url = requestBuilder.buildUrl(
                "https",
                "api.openrouteservice.org",
                "/v2/directions/",
                transportType.getOpenRouteName(),
                Map.of()
        );

        HttpHeaders headers = requestBuilder.buildHeaders(MediaType.APPLICATION_JSON);
        headers.set("Authorization", API_KEY);

        Map<String, Object> body = new HashMap<>();
        body.put("coordinates", coordinates);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        PostRouteResponseDto responseDto = restTemplate.postForObject(url, requestEntity, PostRouteResponseDto.class);
        return OpenRouteMapper.postToGetRouteResponseDto(responseDto);
    }
}
