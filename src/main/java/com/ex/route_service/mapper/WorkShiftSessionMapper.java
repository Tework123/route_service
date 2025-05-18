package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.courierDto.CreateWorkShiftSessionDto;
import com.ex.route_service.entity.WorkShiftSession;
import com.ex.route_service.enums.WorkShiftSessionStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkShiftSessionMapper {
//    public GetLastLocationPointDto toResponseDto(LocationPoint locationPoint) {
//        if (locationPoint == null) return null;
//
//        GetLastLocationPointDto dto = new GetLastLocationPointDto();
//        dto.setLatitude(dto.getLatitude());
//        dto.setLongitude(dto.getLongitude());
//        dto.setDeviceTimestamp(dto.getDeviceTimestamp());
//        return dto;
//    }

    public WorkShiftSession toEntity(CreateWorkShiftSessionDto dto, WorkShiftSessionStatus status, UUID userId) {
        if (dto == null) return null;

        return WorkShiftSession.builder()
                .workShiftSessionStatus(status)
                .userId(userId)
                .startTime(dto.getStartTime())
                .build();
    }

}