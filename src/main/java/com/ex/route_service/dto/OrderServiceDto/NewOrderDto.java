package com.ex.route_service.dto.OrderServiceDto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewOrderDto {
    private UUID orderId;
    private double latitudeRestaurant;
    private double longitudeRestaurant;
    private double latitudeClient;
    private double longitudeClient;
}