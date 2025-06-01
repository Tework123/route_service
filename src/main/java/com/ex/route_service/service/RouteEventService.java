package com.ex.route_service.service;


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
    private final RouteEventMapper routeEventMapper;

    public void save(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint) {
        RouteEvent routeEvent = routeEventMapper.toEntity(routeEventStatus, orderId, courier, locationPoint, null);
        routeEventRepository.save(routeEvent);
    }

    public void save(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint, WeatherStatus weatherStatus) {
        RouteEvent routeEvent = routeEventMapper.toEntity(routeEventStatus, orderId, courier, locationPoint, weatherStatus);
        routeEventRepository.save(routeEvent);
    }

    public void sendRouteEventsForOrder(UUID orderId, UUID courierId) {
        List<RouteEvent> routeEvents = routeEventRepository.findAllByOrderId(orderId);

//TODO маппим ивенты, заказ и курьера и отправляем в сервис
// финансов(спросить у gpt как высчитывать между статусами время, как оплачивать)

    }


}
