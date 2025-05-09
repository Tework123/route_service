package com.ex.route_service.service;

import com.ex.route_service.dto.RouteServiceDto.routeEventDto.CreateRouteEventDto;
import com.ex.route_service.entity.RouteEvent;
import com.ex.route_service.entity.WorkShiftSession;
import com.ex.route_service.mapper.RouteEventMapper;
import com.ex.route_service.repository.RouteEventRepository;
import com.ex.route_service.repository.WorkShiftSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RouteEventService {

    private final RouteEventRepository routeEventRepository;
    private final WorkShiftSessionRepository workShiftSessionRepository;
    private final RouteEventMapper routeEventMapper;

//
    @Transactional
    public void create(CreateRouteEventDto dto) {

        WorkShiftSession workShiftSession = workShiftSessionRepository.findById(dto.getWorkShiftSessionId())
                .orElseThrow(() -> new EntityNotFoundException("session not found with id: " + dto.getWorkShiftSessionId()));

        RouteEvent routeEvent = routeEventMapper.toEntity(dto, workShiftSession);

        routeEventRepository.save(routeEvent);
    }
}
