package com.ex.route_service.dto.RouteServiceDto.locationPointDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLocationPointDto {
    private UUID courierId;
    private LocationDto locationDto;
}
