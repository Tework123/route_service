package com.ex.route_service.dto.RouteServiceDto.routeEventDto;

import com.ex.route_service.enums.RouteEventStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRouteEventDto {
    private UUID orderId;
    private RouteEventStatus routeEventStatus;
    private String message;
}
