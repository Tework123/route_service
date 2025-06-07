package com.ex.route_service.dto.RouteServiceDto.locationPointDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private double longitude;
    private double latitude;
    private LocalDateTime timestamp;

}