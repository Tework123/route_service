package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.CreateLocationRequestDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.GetLastLocationPointDto;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.WorkShiftSession;
import org.springframework.stereotype.Component;

@Component
public class LocationPointMapper {
    public GetLastLocationPointDto toResponseDto(LocationPoint locationPoint) {
        if (locationPoint == null) return null;

        GetLastLocationPointDto dto = new GetLastLocationPointDto();
        dto.setLatitude(dto.getLatitude());
        dto.setLongitude(dto.getLongitude());
        dto.setDeviceTimestamp(dto.getDeviceTimestamp());
        return dto;
    }

    public LocationPoint toEntity(CreateLocationRequestDto dto, WorkShiftSession workShiftSession) {
        if (dto == null) return null;

        return LocationPoint.builder()
                .workShiftSession(workShiftSession)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .timestamp(dto.getTimestamp())
                .build();
    }

}
