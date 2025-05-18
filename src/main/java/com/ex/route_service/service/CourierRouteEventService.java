package com.ex.route_service.service;

import com.ex.route_service.entity.WorkShiftSession;
import com.ex.route_service.repository.CourierRouteEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourierRouteEventService {

    private final CourierRouteEventRepository courierRouteEventRepository;
//    private final CourierRouteEventMapper routeEventMapper;

//
//    @Transactional
//    public void create(CreateRouteEventDto dto) {
//
//        WorkShiftSession workShiftSession = workShiftSessionRepository.findById(dto.getWorkShiftSessionId())
//                .orElseThrow(() -> new EntityNotFoundException("session not found with id: " + dto.getWorkShiftSessionId()));
//
//        RouteEvent routeEvent = routeEventMapper.toEntity(dto, workShiftSession);
//
//        routeEventRepository.save(routeEvent);
//    }
}
