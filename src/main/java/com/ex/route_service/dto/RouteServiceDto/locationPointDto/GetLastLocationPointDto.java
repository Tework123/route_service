package com.ex.route_service.dto.RouteServiceDto.locationPointDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetLastLocationPointDto {
    private double latitude;
    private double longitude;
    private LocalDateTime deviceTimestamp;
}
