package com.ex.route_service.service;


import com.ex.route_service.dto.RouteServiceDto.routeEventDto.CreateRouteEventDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.RouteEvent;
import com.ex.route_service.enums.RouteEventStatus;
import com.ex.route_service.mapper.RouteEventMapper;
import com.ex.route_service.repository.RouteEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RouteEventService {

    private final RouteEventRepository routeEventRepository;
    private final RouteEventMapper routeEventMapper;

    public void save(RouteEventStatus routeEventStatus, UUID orderId, Courier courier, LocationPoint locationPoint) {

        RouteEvent routeEvent = routeEventMapper.toEntity(routeEventStatus, orderId, courier, locationPoint);

        routeEventRepository.save(routeEvent);
    }


}
