package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.vehicleDto.CreateVehicleRequestDto;
import com.ex.route_service.dto.RouteServiceDto.vehicleDto.GetVehicleResponseDto;
import com.ex.route_service.dto.RouteServiceDto.vehicleDto.UpdateVehicleDto;
import com.ex.route_service.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public GetVehicleResponseDto toResponseDto(Vehicle vehicle) {
        if (vehicle == null) return null;

        GetVehicleResponseDto dto = new GetVehicleResponseDto();
        dto.setName(vehicle.getName());
        dto.setGovNumber(vehicle.getGovNumber());
        dto.setVehicleStatus(vehicle.getVehicleStatus());
        dto.setTimeCreate(vehicle.getTimeCreate());
        dto.setTimeLastSeenAt(vehicle.getTimeLastSeenAt());
        return dto;
    }

    public Vehicle toEntity(CreateVehicleRequestDto dto) {
        if (dto == null) return null;

        return Vehicle.builder()
                .name(dto.getName()).build();
    }

    public void updateDeviceFromDto(Vehicle vehicle, UpdateVehicleDto dto) {
        if (dto.getName() != null) {
            vehicle.setName(dto.getName());
        }
    }
}
