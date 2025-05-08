package com.ex.route_service.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GeoIpService {
    private static final String BASE_URL = "https://nominatim.openstreetmap.org/search";


    private final RestTemplate restTemplate;

    public List<Map<String, Object>> getCountryCodeByIp(String query) {
        String url = String.format("%s?q=%s&format=json&addressdetails=1&limit=1", BASE_URL, query);

        System.out.println(url);

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        return response.getBody();
    }
}
