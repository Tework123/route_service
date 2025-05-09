package com.ex.route_service.dto.RouteServiceDto.vehicleDto;

import com.ex.route_service.enums.VehicleStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO для вывода информации об устройстве.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetVehicleResponseDto {
    private String name;
    private String govNumber;
    private VehicleStatus vehicleStatus;
    private LocalDateTime timeCreate;
    private LocalDateTime timeLastSeenAt;


}
