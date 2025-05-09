package com.ex.route_service.dto.RouteServiceDto.workShiftSessionDto;

import com.ex.route_service.enums.RouteEventStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWorkShiftSessionDto {
    private LocalDateTime startTime;
}