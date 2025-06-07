package com.ex.route_service.service;


import com.ex.route_service.client.FinanceServiceClient;
import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.RouteEvent;
import com.ex.route_service.enums.RouteEventStatus;
import com.ex.route_service.enums.WeatherStatus;
import com.ex.route_service.mapper.RouteEventMapper;
import com.ex.route_service.repository.RouteEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteEventService {

    private final RouteEventRepository routeEventRepository;
    private final FinanceServiceClient financeServiceClient;

    public void save(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint) {
        RouteEvent routeEvent = RouteEventMapper.toEntity(routeEventStatus, orderId, courier, locationPoint, null);
        routeEventRepository.save(routeEvent);
    }

    public void save(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint, WeatherStatus weatherStatus) {
        RouteEvent routeEvent = RouteEventMapper.toEntity(routeEventStatus, orderId, courier, locationPoint, weatherStatus);
        routeEventRepository.save(routeEvent);
    }

    public void sendRouteEvents(UUID orderId, UUID courierId) {
        List<RouteEvent> routeEvents = routeEventRepository.findAllByOrderId(orderId);
        SendRouteEventsRequestDto requestDto = RouteEventMapper.toSendRouteEventsRequestDto(routeEvents, courierId, orderId);
        financeServiceClient.sendRouteEvents(requestDto);
    }


}
