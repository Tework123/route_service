package com.ex.route_service.dto.RouteServiceDto.vehicleDto;

import lombok.*;

/**
 * DTO для регистрации устройства.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateVehicleRequestDto {
    private String name;
}
