package com.ex.route_service.mapper;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.dto.RouteServiceDto.routeEventDto.CreateRouteEventDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.RouteEvent;
import com.ex.route_service.enums.RouteEventStatus;
import com.ex.route_service.enums.WeatherStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RouteEventMapper {


    public CreateRouteEventDto toCreateRouteEventDto(RouteEventStatusRequestDto statusRequestDto) {
        CreateRouteEventDto createRouteEventDto = new CreateRouteEventDto();
        createRouteEventDto.setRouteEventStatus(statusRequestDto.getRouteEventStatus());
        createRouteEventDto.setOrderId(statusRequestDto.getOrderId());
        return createRouteEventDto;
    }


    public static RouteEvent toEntity(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint, WeatherStatus weatherStatus) {
        if (routeEventStatus == null) return null;

        RouteEvent routeEvent = new RouteEvent();
        routeEvent.setTimestamp(routeEvent.getTimestamp());
        routeEvent.setCourier(courier);
        routeEvent.setOrderId(orderId);
        routeEvent.setRouteEventStatus(routeEvent.getRouteEventStatus());
        routeEvent.setLocationPoint(locationPoint);
        routeEvent.setWeatherStatus(weatherStatus);

        return routeEvent;
    }

    public static SendRouteEventsRequestDto toSendRouteEventsRequestDto(List<RouteEvent> routeEvents, UUID courierId, UUID orderId) {
        return SendRouteEventsRequestDto.builder()
                .courierId(courierId)
                .orderId(orderId)
                .routeEvents(toRouteEventDto(routeEvents))
                .build();
    }


    public static List<SendRouteEventsRequestDto.RouteEventDto> toRouteEventDto(List<RouteEvent> routeEvents) {
        return routeEvents == null ? new ArrayList<>()
                : routeEvents.stream().map(RouteEventMapper::toRouteEventDto).collect(Collectors.toList());
    }

    public static SendRouteEventsRequestDto.RouteEventDto toRouteEventDto(RouteEvent routeEvent) {
        return SendRouteEventsRequestDto.RouteEventDto.builder()
                .routeEventId(routeEvent.getRouteEventId())
                .timestamp(routeEvent.getTimestamp())
                .timeCreate(routeEvent.getTimeCreate())
                .routeEventStatus(routeEvent.getRouteEventStatus())
                .message(routeEvent.getMessage())
                .locationPoint(LocationPointMapper.toSendRouteDto(routeEvent.getLocationPoint()))
                .weatherStatus(routeEvent.getWeatherStatus())
                .build();

    }
}
