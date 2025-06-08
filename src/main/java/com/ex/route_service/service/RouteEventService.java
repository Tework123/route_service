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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с маршрутными событиями курьеров.
 * Отвечает за сохранение событий и отправку их во внешний сервис.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RouteEventService {

    private final RouteEventRepository routeEventRepository;
    private final FinanceServiceClient financeServiceClient;

    /**
     * Сохраняет маршрутное событие в базе данных.
     *
     * @param routeEventStatus статус события маршрута
     * @param orderId          идентификатор заказа
     * @param courier          курьер, связанный с событием
     * @param locationPoint    точка местоположения, связанная с событием
     * @param weatherStatus    погодные условия во время события
     */
    public void save(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint, WeatherStatus weatherStatus) {
        RouteEvent routeEvent = RouteEventMapper.toEntity(routeEventStatus, orderId, courier, locationPoint, weatherStatus);
        routeEventRepository.save(routeEvent);
        log.info("Произошло событие {} у курьера {}", routeEventStatus, courier.getCourierId());

    }

    /**
     * Отправляет маршрутные события в финансовый сервис.
     *
     * @param orderId   идентификатор заказа
     * @param courierId идентификатор курьера
     */
    public void sendRouteEvents(UUID orderId, UUID courierId) {
        List<RouteEvent> routeEvents = routeEventRepository.findAllByOrderId(orderId);
        SendRouteEventsRequestDto requestDto = RouteEventMapper.toSendRouteEventsRequestDto(routeEvents, courierId, orderId);
        financeServiceClient.sendRouteEvents(requestDto);
    }
}
