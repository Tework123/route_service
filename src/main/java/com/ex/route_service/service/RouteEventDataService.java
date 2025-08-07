package com.ex.route_service.service;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.entity.RouteEvent;
import com.ex.route_service.mapper.RouteEventMapper;
import com.ex.route_service.repository.RouteEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteEventDataService {
    private final RouteEventRepository routeEventRepository;

    public List<SendRouteEventsRequestDto.RouteEventDto> getAllRouteEvents() {
        List<RouteEvent> routeEvents = routeEventRepository.findAll();
        return RouteEventMapper.toRouteEventDto(routeEvents);
    }

}
