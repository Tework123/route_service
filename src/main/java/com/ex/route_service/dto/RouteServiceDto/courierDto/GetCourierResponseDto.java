package com.ex.route_service.dto.RouteServiceDto.courierDto;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.TransportType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCourierResponseDto {
    private UUID courierId;
    private TransportType transportType;
    private CourierStatus courierStatus;
    private LocationDto currentLocation;
}
