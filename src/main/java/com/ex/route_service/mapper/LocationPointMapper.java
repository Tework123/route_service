package com.ex.route_service.mapper;

import com.ex.route_service.Utils.GeometryUtils;
import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.CreateLocationPointDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.RouteEvent;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

public class LocationPointMapper {

    public static LocationPoint toEntity(LocationDto dto, Courier courier, RouteEvent routeEvent) {
        if (dto == null) return null;

        Point point = GeometryUtils.toPoint(dto.getLatitude(), dto.getLongitude());

        return LocationPoint.builder()
                .location(point)
                .courier(courier)
                .routeEvent(routeEvent)
                .timestamp(dto.getTimestamp())
                .build();
    }

    public CreateLocationPointDto toCreateLocationPointDto(RouteEventStatusRequestDto statusRequestDto, UUID courierId) {
        if (statusRequestDto == null) return null;
        CreateLocationPointDto locationPointDto = new CreateLocationPointDto();
        locationPointDto.setCourierId(courierId);
        locationPointDto.setLocationDto(statusRequestDto.getLocationDto());

        return locationPointDto;

    }

    public CreateLocationPointDto toCreateLocationPointDto(LocationDto locationDto, UUID courierId) {
        if (locationDto == null) return null;
        CreateLocationPointDto locationPointDto = new CreateLocationPointDto();
        locationPointDto.setCourierId(courierId);
        locationPointDto.setLocationDto(locationDto);

        return locationPointDto;

    }

    public static SendRouteEventsRequestDto.LocationPointDto toDto(LocationPoint locationPoint){
        return SendRouteEventsRequestDto.LocationPointDto.builder()
                .locationPointId(locationPoint.getLocationPointId())
                .longitude(locationPoint.getLocation().getX())
                .latitude(locationPoint.getLocation().getY())
                .timeCreate(locationPoint.getTimeCreate())
                .timestamp(locationPoint.getTimestamp())
                .build();
    }

}
