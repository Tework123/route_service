package com.ex.route_service.client;

import com.ex.route_service.enums.TransportType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class OpenRouteClient {

    private final RestTemplate restTemplate;

    private final RequestBuilder requestBuilder;

    private static final String API_KEY = "5b3ce3597851110001cf62483f26b17e6a754540adf28bc137fa9fed";

    //мейби здесь не string возвращать, а лист дтошек
    public String getRoute(List<List<Double>> coordinates, TransportType transportType) {
        Map<String, String> params = Map.of("api_key", API_KEY);

        String url = requestBuilder.buildUrl(
                "https",
                "api.openrouteservice.org",
                "/v2/directions/",
                transportType.getOpenRouteName(),
                params
        );

        HttpHeaders headers = requestBuilder.buildHeaders(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("coordinates", coordinates);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, requestEntity, String.class);
    }
}
