package com.ex.route_service.dto.OrderServiceDto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetWorkShiftSessionsDto {
    private double latitudeRestaurant;
    private double longitudeRestaurant;
    private double latitudeClient;
    private double longitudeClient;
}