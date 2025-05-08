package com.ex.route_service.controller;

import com.ex.route_service.dto.locationPointDto.CreateLocationRequestDto;
import com.ex.route_service.dto.locationPointDto.GetLastLocationPointDto;
import com.ex.route_service.dto.routeEventDto.CreateRouteEventDto;
import com.ex.route_service.service.LocationService;
import com.ex.route_service.service.RouteEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/route_event")
@AllArgsConstructor
public class RouteEventController {

    private final RouteEventService routeEventService;

// Принять заказ (хотя вроде надо из сервиса заказов по rest template  принимать запрос, пока так)
    @PostMapping
    public ResponseEntity<Void> acceptOrderByCourier(@RequestBody CreateRouteEventDto request) {
        routeEventService.create(request);
        return ResponseEntity.ok().build();
    }


}
