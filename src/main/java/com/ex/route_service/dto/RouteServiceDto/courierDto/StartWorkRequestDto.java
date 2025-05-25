package com.ex.route_service.dto.RouteServiceDto.courierDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartWorkRequestDto {
    private LocalDateTime timestamp;
    private double latitude;
    private double longitude;
}