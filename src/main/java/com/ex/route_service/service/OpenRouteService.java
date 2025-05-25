package com.ex.route_service.service;

import com.ex.route_service.client.OpenRouteClient;
import com.ex.route_service.enums.TransportType;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OpenRouteService {

    private final OpenRouteClient openRouteClient;


    public String getRoute(Double longitudeCourier, Double latitudeCourier,
                           Double longitudeRestaurant, Double latitudeRestaurant,
                           Double longitudeClient, Double latitudeClient,
                           TransportType transportType) {
        List<List<Double>> coordinates;

        coordinates = List.of(
                List.of(longitudeCourier, latitudeCourier),
                List.of(longitudeRestaurant, latitudeRestaurant),
                List.of(longitudeClient, latitudeClient)
        );

        return openRouteClient.getRoute(coordinates, transportType);
    }

    public String getRoute(Double longitudeCourier, Double latitudeCourier,
                           Double longitudeClient, Double latitudeClient,
                           TransportType transportType) {
        List<List<Double>> coordinates;

        coordinates = List.of(
                List.of(longitudeCourier, latitudeCourier),
                List.of(longitudeClient, latitudeClient)
        );

        return openRouteClient.getRoute(coordinates, transportType);
    }
}
