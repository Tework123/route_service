package com.ex.route_service.controller;

import com.ex.route_service.dto.RouteServiceDto.routeEventDto.CreateRouteEventDto;
import com.ex.route_service.service.RouteEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route_event")
@AllArgsConstructor
public class RouteEventController {

    private final RouteEventService routeEventService;

    // Принять заказ (хотя вроде надо из сервиса заказов по rest template  принимать запрос, пока так)
//    Короче когда водитель нажимает на кнопки работы с заказом,
//    то все запросы отправляются на сервис заказов, меняется его статус,
//    только потом сервис заказов отправляет запрос на изменение статуса рабочей сессии курьера.
//    Вернее добавление новой записи от изменении статуса сессии, дабы хранить историю.
//    Но еще эта api принимает запросы прямо сюда, без участия заказов,
    @PostMapping
    public ResponseEntity<Void> createRouteEventStatus(@RequestBody CreateRouteEventDto request) {
        routeEventService.create(request);
        return ResponseEntity.ok().build();
    }

}
