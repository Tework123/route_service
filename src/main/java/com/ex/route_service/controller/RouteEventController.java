package com.ex.route_service.controller;

import com.ex.route_service.service.RouteEventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route_event")
@AllArgsConstructor
public class RouteEventController {

    private final RouteEventService routeEventService;

}
