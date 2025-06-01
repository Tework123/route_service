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

//    из сервиса (serviceRouteEvent) можно брать статусы для  расчета оплаты работы курьера(для сервиса денюжек).
//    пройденное расстояние, доставленные заказы, оценка курьера,  ожидание в ресторане
//    будет отправляется после доставки заказа, вызов сервисного метода для отправки по rest
//    в сервис денюжек всю инфу от расстоянии, статусах во время доставки(погода).

// курьер завершает смену - отправляется запрос в сервис финансов с длительностью сессии(8 часов)
//    за каждый час 50 руб

// по поводу прибавки за погоду, можно делать запрос в сервис погоды в конкретном городе(районе)
//    добавить weather в entity routeEventStatus. При взятии заказа будет запрос в сервис погоды, потом
//    добавляется статус средняя, хорошая, плохая, очень плохая.(как будто может меняться во время заказа)

//    расстояние. Здесь надо после доставки заказа отправлять запрос с расстоянием(которое openRoute отправил)
//    или это после смены статуса в courierService(за км 10 руб, заебись)

//    ожидание в ресторане. После доставки, отправляются также все статусы от take_order до delivered
//    а там высчитывает насколько много времени затратил на каждую часть доставки курьер.
}
