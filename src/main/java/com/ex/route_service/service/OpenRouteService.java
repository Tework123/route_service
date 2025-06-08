package com.ex.route_service.service;

import com.ex.route_service.client.OpenRouteClient;
import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.enums.TransportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для получения маршрутов от OpenRouteService.
 */
@Service
@RequiredArgsConstructor
public class OpenRouteService {

    private final OpenRouteClient openRouteClient;


    /**
     * Получает маршрут от точки курьера до ресторана и далее до клиента.
     *
     * @param longitudeCourier    долгота местоположения курьера
     * @param latitudeCourier     широта местоположения курьера
     * @param longitudeRestaurant долгота местоположения ресторана
     * @param latitudeRestaurant  широта местоположения ресторана
     * @param longitudeClient     долгота местоположения клиента
     * @param latitudeClient      широта местоположения клиента
     * @param transportType       тип транспорта, используемый курьером
     * @return маршрут, включающий путь от курьера к ресторану и от ресторана к клиенту
     */
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

    /**
     * Получает маршрут от курьера напрямую до клиента (минуя ресторан).
     *
     * @param longitudeCourier долгота местоположения курьера
     * @param latitudeCourier  широта местоположения курьера
     * @param longitudeClient  долгота местоположения клиента
     * @param latitudeClient   широта местоположения клиента
     * @param transportType    тип транспорта, используемый курьером
     * @return маршрут от курьера до клиента
     */
    public GetRouteResponseDto getRoute(Double longitudeCourier, Double latitudeCourier,
                           Double longitudeClient, Double latitudeClient,
                           TransportType transportType) {
        return openRouteClient.getRoute(longitudeCourier, latitudeCourier,
                longitudeClient, latitudeClient,
                transportType);
    }
}
