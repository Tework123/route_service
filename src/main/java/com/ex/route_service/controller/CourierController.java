package com.ex.route_service.controller;


import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courier")
@AllArgsConstructor
public class CourierController {

    private final CourierService courierService;

    //    возвращает всю инфу по курьеру, используется в том числе для других сервисах
    @GetMapping("/{courierId}")
    public GetCourierResponseDto getCourier(@PathVariable UUID courierId) {
        return courierService.getCourier(courierId);
    }

    //    возвращает доступных по статусу и расстоянию курьеров для нового заказа
//    запрос делает сервис заказов при появлении нового заказа,
//    чтобы отправить уведомления незанятым ближайшим курьерами
    @GetMapping("/order/{orderId}")
    public List<GetCouriersForOrderResponseDto> getCouriersForOrder(
            @RequestParam double longitudeClient,
            @RequestParam double latitudeClient,
            @RequestParam double longitudeRestaurant,
            @RequestParam double latitudeRestaurant,
            @PathVariable UUID orderId) {
        return courierService.getCouriersForOrder(longitudeClient,
                latitudeClient,
                longitudeRestaurant,
                latitudeRestaurant,
                orderId);
    }

    //    получает маршрут для доставки заказа
    @GetMapping("/route/{orderId}")
    public GetRouteResponseDto getRoute(@RequestParam double latitudeClient,
                                        @RequestParam double longitudeClient,
                                        @RequestParam double latitudeRestaurant,
                                        @RequestParam double longitudeRestaurant,
                                        @RequestParam UUID courierId,
                                        @PathVariable UUID orderId) throws Exception {

        return courierService.getRoute(
                longitudeRestaurant, latitudeRestaurant,
                longitudeClient, latitudeClient, courierId, orderId);

    }

    //   может прийти как из сервиса заказов, так и из мобилки курьера, в зависимости от статуса курьера
//    если из заказов, то добавляется orderId, хотя и из мобилки можно, если есть
    @PutMapping("/change_status/{courierId}")
    public ResponseEntity<Void> changeCourierStatus(@RequestBody RouteEventStatusRequestDto request,
                                                    @PathVariable UUID courierId) {
        courierService.changeCourierStatus(courierId, request);
        return ResponseEntity.ok().build();
    }


//    estimatedArrivalTime


//    todo: 1. проверить функционал готового кода
//    todo: 2 добавить доп функционал работы с картами, геозонами
    //    todo: 2. добавить redis для сохранения в кеш текущего положения курьера(последняя точка из locationPoint)
//    todo: 3. security и тд по документу

//  специально для повторяющихся запросов, еще бы редис вставить  getRoute(coordintes, )
}
