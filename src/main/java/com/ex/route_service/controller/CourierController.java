package com.ex.route_service.controller;


import com.ex.route_service.dto.OrderServiceDto.NewOrderDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.routeEventDto.StartWorkRequestDto;
import com.ex.route_service.mapper.CourierMapper;
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


    @PostMapping("/start/{userId}")
    public ResponseEntity<Void> startWork(@RequestBody StartWorkRequestDto request,
                                          @PathVariable UUID userId) {
        courierService.startWork(request, userId);
        return ResponseEntity.ok().build();
    }

    //    возвращает всю инфу по курьеру, используется в том числе для других сервисов

    // как выглядит для курьера запрос: тыкает кнопку, отправляются его координаты,
// транспорт и статус в order, нет делается запрос сюда, уже сделал,
// там логика работает по расстояниям - выдаются ближайшие заказы
    @GetMapping("/{courierId}")
    public GetCourierResponseDto getCourier(@PathVariable UUID courierId) {
        return courierService.getCourier(courierId);
    }


    //    возвращает доступных по статусу и расстоянию курьеров для нового заказа
//    запрос делает сервис заказов при появлении нового заказа,
//    чтобы отправить уведомления незанятым ближайшим курьерами
    @GetMapping("/order/{orderId}")
    public List<GetCouriersForOrderResponseDto> getCouriersForOrder(@RequestParam double latitudeClient,
                                                                    @RequestParam double longitudeClient,
                                                                    @RequestParam double latitudeRestaurant,
                                                                    @RequestParam double longitudeRestaurant,
                                                                    @PathVariable UUID orderId) {
        return courierService.getCouriersForOrder(latitudeClient,
                longitudeClient,
                latitudeRestaurant,
                longitudeRestaurant,
                orderId);
    }

//    курьер хочет получить маршрут до ресторана и до клиента, тыкает на кнопку(еще не принял заказ)
//    также может кликать на кнопку эту, когда уже работает с заказом.

//    надо как то определить, когда 3 координаты, а когда 2
//    если у заказа статус взят из ресторана, и следующие статусы - 2 точки, иначе 3

    //  кнопка построить маршрут: если курьер не взял заказ, взял(до получения из ресторана), взял(после получения)
//   сюда прилитает из телефона курьера информация о заказе, в том числе коодинаты рестика и клиента, и курьера
    @GetMapping("/route/{orderId}")
    public String getRoute(@RequestParam double latitudeClient,
                          @RequestParam double longitudeClient,
                          @RequestParam double latitudeRestaurant,
                          @RequestParam double longitudeRestaurant,
                          @RequestParam double latitudeCourier,
                          @RequestParam double longitudeCourier,
                          @RequestParam UUID courierId,
                          @PathVariable UUID orderId) throws Exception {

        String route = courierService.getRoute(longitudeCourier, latitudeCourier,
                longitudeRestaurant, latitudeRestaurant,
                longitudeClient, latitudeClient, courierId, orderId);

        return route;

    }
//    estimatedArrivalTime


//  специально для повторяющихся запросов, еще бы редис вставить  getRoute(coordintes, )
}
