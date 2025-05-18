package com.ex.route_service.service;

import com.ex.route_service.client.OpenRouteClient;
import com.ex.route_service.enums.TransportType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OpenRouteService {

    private final OpenRouteClient openRouteClient;


    public String getRoute(Double longitudeCourier, Double latitudeCourier,
                           Double longitudeRestaurant, Double latitudeRestaurant,
                           Double longitudeClient, Double latitudeClient, TransportType transportType) {
        List<List<Double>> coordinates;

        if (longitudeRestaurant != null && latitudeRestaurant != null) {
            coordinates = List.of(
                    List.of(longitudeCourier, latitudeCourier),
                    List.of(longitudeRestaurant, latitudeRestaurant),
                    List.of(longitudeClient, latitudeClient)
            );
        } else {
            coordinates = List.of(
                    List.of(longitudeCourier, latitudeCourier),
                    List.of(longitudeClient, latitudeClient)
            );
        }

        return openRouteClient.getRoute(coordinates, transportType);
    }
}
