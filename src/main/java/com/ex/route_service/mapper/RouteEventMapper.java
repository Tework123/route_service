package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.routeEventDto.CreateRouteEventDto;
import com.ex.route_service.entity.RouteEvent;
import com.ex.route_service.entity.WorkShiftSession;
import org.springframework.stereotype.Component;

@Component
public class RouteEventMapper {

//    public GetLastLocationPointDto toResponseDto(RouteEvent routeEvent) {
//        if (routeEvent == null) return null;
//
//        GetLastLocationPointDto dto = new GetLastLocationPointDto();
//        dto.setLatitude(dto.getLatitude());
//        dto.setLongitude(dto.getLongitude());
//        dto.setDeviceTimestamp(dto.getDeviceTimestamp());
//        return dto;
//    }

    public RouteEvent toEntity(CreateRouteEventDto dto, WorkShiftSession workShiftSession) {
        if (dto == null) return null;

        return RouteEvent.builder()
                .workShiftSession(workShiftSession)
                .routeEventStatus(dto.getRouteEventStatus())
                .timeCreate(dto.getTimeCreate())
                .message(dto.getMessage())
                .orderId(dto.getOrderId())
                .build();
    }
}
