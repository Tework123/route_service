package com.ex.route_service.dto.RouteServiceDto.courierDto;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.enums.TransportType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCourierRequestDto {
    private TransportType transportType;
    private LocationDto currentLocation;

}
