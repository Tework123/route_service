package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.routeEventDto.StartWorkRequestDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.CourierRouteEvent;
import com.ex.route_service.enums.CourierRouteEventStatus;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class CourierRouteEventMapper {

//    public GetLastLocationPointDto toResponseDto(RouteEvent routeEvent) {
//        if (routeEvent == null) return null;
//
//        GetLastLocationPointDto dto = new GetLastLocationPointDto();
//        dto.setLatitude(dto.getLatitude());
//        dto.setLongitude(dto.getLongitude());
//        dto.setDeviceTimestamp(dto.getDeviceTimestamp());
//        return dto;
//    }

    public CourierRouteEvent toEntity(StartWorkRequestDto dto, CourierRouteEventStatus status, Courier courier) {
        if (dto == null) return null;

// надо в отдельный сервис вынести
        GeometryFactory geometryFactory = new GeometryFactory();
        //        преобразование double Longitude и Latitude в Point
        Coordinate coordinate = new Coordinate(dto.getLongitude(), dto.getLatitude());
        Point point = geometryFactory.createPoint(coordinate);

        return CourierRouteEvent.builder()
                .courier(courier)
                .location(point)
                .courierRouteEventStatus(status)
                .timestamp(dto.getTimestamp())
                .build();
    }
}
