package com.ex.route_service.dto.RouteServiceDto.courierDto;

import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.TransportType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCourierRouteResponseDto {
    private UUID courierId;
//    здесь нужны маршруты, вложенная dto будет
    private TransportType transportType;
    private CourierStatus courierStatus;
}
