package com.ex.route_service.controller;


import com.ex.route_service.dto.OpenRouteServiceDto.GetRouteResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.CreateCourierRequestDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.RouteEventStatusRequestDto;
import com.ex.route_service.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для управления курьерами.
 */
@RestController
@RequestMapping("/courier")
@AllArgsConstructor
public class CourierController {
    private final CourierService courierService;

    /**
     * Возвращает информацию о курьере по его UUID.
     *
     * @param courierId UUID курьера
     * @return DTO с информацией о курьере
     */
    @GetMapping("/{courierId}")
    public GetCourierResponseDto getCourier(@PathVariable UUID courierId) {
        return courierService.getCourier(courierId);
    }

    /**
     * Создает нового курьера.
     *
     * @param request DTO с данными для создания курьера
     * @return пустой ответ с HTTP 200 OK
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createCourier(@RequestBody CreateCourierRequestDto request) {

        courierService.createCourier(request);
        return ResponseEntity.ok().build();

    }

    /**
     * Возвращает список доступных курьеров для нового заказа.
     *
     * @param longitudeClient долгота клиента
     * @param latitudeClient  широта клиента
     * @param longitudeRestaurant долгота ресторана
     * @param latitudeRestaurant  широта ресторана
     * @param orderId UUID заказа
     * @return список курьеров, подходящих для заказа
     */
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

    /**
     * Получает маршрут для доставки заказа.
     *
     * @param latitudeClient широта клиента
     * @param longitudeClient долгота клиента
     * @param latitudeRestaurant широта ресторана
     * @param longitudeRestaurant долгота ресторана
     * @param courierId UUID курьера
     * @param orderId UUID заказа
     * @return DTO с маршрутом доставки
     */
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

    /**
     * Изменяет статус курьера.
     *
     * @param request DTO с новым статусом маршрута
     * @param courierId UUID курьера
     * @return пустой ответ с HTTP 200 OK
     */
    @PutMapping("/change_status/{courierId}")
    public ResponseEntity<Void> changeCourierStatus(@RequestBody RouteEventStatusRequestDto request,
                                                    @PathVariable UUID courierId) {
        courierService.changeCourierStatus(courierId, request);
        return ResponseEntity.ok().build();
    }


    //    TODO логирования в нужных местах(опыт+ gpt)
//    TODO security и исключения
//    TODO чистка кода всего

//    todo: 1. проверить функционал готового кода
//    todo: 2 добавить доп функционал работы с картами, геозонами
    //    todo: 2. добавить redis для сохранения в кеш текущего положения курьера(последняя точка из locationPoint)
//    todo: 3. security и тд по документу

}
