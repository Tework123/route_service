package com.ex.route_service.dto.RouteServiceDto.locationPointDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LocationResponseDto {
    private double longitude;
    private double latitude;
    private LocalDateTime timestamp;
}
