package com.ex.route_service.service;

import com.ex.route_service.AppStartupLogger;
import com.ex.route_service.dto.RouteServiceDto.vehicleDto.CreateVehicleRequestDto;
import com.ex.route_service.dto.RouteServiceDto.vehicleDto.GetVehicleResponseDto;
import com.ex.route_service.dto.RouteServiceDto.vehicleDto.UpdateVehicleDto;
import com.ex.route_service.entity.Vehicle;
import com.ex.route_service.mapper.VehicleMapper;
import com.ex.route_service.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class VehicleService {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupLogger.class);

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;



    @Transactional
    public void save(CreateVehicleRequestDto createVehicleRequestDto) {
        Vehicle vehicle = vehicleMapper.toEntity(createVehicleRequestDto);

        vehicleRepository.save(vehicle);

        logger.info("Устройство создано с id {}", vehicle.getVehicleId());
    }

    public GetVehicleResponseDto getVehicleById(UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()
                -> new EntityNotFoundException("Device not found with id: " + vehicleId));

        return vehicleMapper.toResponseDto(vehicle);
    }

    public void update(UUID vehicleId, UpdateVehicleDto dto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()
                -> new EntityNotFoundException("Device not found with id: " + vehicleId));

        vehicleMapper.updateDeviceFromDto(vehicle, dto);

        vehicleRepository.save(vehicle);

    }

    public void delete(UUID vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new EntityNotFoundException("Device not found with id: " + vehicleId);
        }

        vehicleRepository.deleteById(vehicleId);
        logger.info("Устройство удалено с id {}", vehicleId);

    }
}
