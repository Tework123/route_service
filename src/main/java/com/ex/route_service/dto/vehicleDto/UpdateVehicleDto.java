package com.ex.route_service.dto.vehicleDto;

import com.ex.route_service.enums.VehicleStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVehicleDto {
    private String name;
    private VehicleStatus vehicleStatus;
    private String govNumber;

}
