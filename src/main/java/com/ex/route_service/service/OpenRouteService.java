package com.ex.route_service.service;

import com.ex.route_service.client.OpenRouteClient;
import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.enums.TransportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OpenRouteService {

    private final OpenRouteClient openRouteClient;


    public GetRouteResponseDto getRoute(Double longitudeCourier, Double latitudeCourier,
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

    public GetRouteResponseDto getRoute(Double longitudeCourier, Double latitudeCourier,
                           Double longitudeClient, Double latitudeClient,
                           TransportType transportType) {
        return openRouteClient.getRoute(longitudeCourier, latitudeCourier,
                longitudeClient, latitudeClient,
                transportType);
    }
}
