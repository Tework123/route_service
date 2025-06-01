package com.ex.route_service.dto.RouteServiceDto.courierDto;


import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.OrderStatus;
import com.ex.route_service.enums.RouteEventStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteEventStatusRequestDto {
    private RouteEventStatus routeEventStatus;
    private UUID orderId;
    private LocationDto locationDto;
}
