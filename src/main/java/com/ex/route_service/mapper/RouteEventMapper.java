package com.ex.route_service.mapper;

import com.ex.route_service.Utils.GeometryUtils;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.StartWorkRequestDto;
import com.ex.route_service.dto.RouteServiceDto.routeEventDto.CreateRouteEventDto;
import com.ex.route_service.entity.Courier;

import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.RouteEvent;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.RouteEventStatus;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RouteEventMapper {

    public CreateRouteEventDto toCreateRouteEventDto(RouteEventStatusRequestDto statusRequestDto) {
        CreateRouteEventDto createRouteEventDto = new CreateRouteEventDto();
        createRouteEventDto.setRouteEventStatus(statusRequestDto.getRouteEventStatus());
        createRouteEventDto.setOrderId(statusRequestDto.getOrderId());
        return createRouteEventDto;
    }


    public RouteEvent toEntity(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint) {
        if (routeEventStatus == null) return null;

        RouteEvent routeEvent = new RouteEvent();
        routeEvent.setTimestamp(routeEvent.getTimestamp());
        routeEvent.setCourier(courier);
        routeEvent.setOrderId(orderId);
        routeEvent.setRouteEventStatus(routeEvent.getRouteEventStatus());
        routeEvent.setLocationPoint(locationPoint);

        return routeEvent;
    }
}
