package com.ex.route_service.dto.locationPointDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLocationRequestDto {
    private UUID workShiftSessionId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
