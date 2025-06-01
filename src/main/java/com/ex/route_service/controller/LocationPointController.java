package com.ex.route_service.controller;

import com.ex.route_service.service.LocationPointService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для приёма координатных данных от устройств.
 */
@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationPointController {
    //TODO изменить название, этот контроллер принимает координаты курьера и пишет в базу
//     надо менять как у курьера последнюю точку, так и добавлять запись для истории

    private final LocationPointService locationPointService;

    /**
     * Принимает координаты устройства и сохраняет их в систему.
     *
     * @param request объект запроса, содержащий координаты и время отправки
     * @return HTTP-ответ 200 OK в случае успешного сохранения
     */
//    @PostMapping
//    public ResponseEntity<Void> createLocationPoint(@RequestBody CreateLocationRequestDto request) {
//        locationService.save(request);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/last/{deviceId}")
//    public GetLastLocationPointDto getLastLocationPoint(@PathVariable UUID deviceId) {
//        return locationService.getLast(deviceId);
//    }
//   придумать логику, несколько сложных сервисов с координатами, потом миграции:
//    как выводить инфу, где новый маршрут начинается
//     javadoc
//    TODO Получение маршрута устройства(сделать завтра), сделал записи в гугл,
//     норм логика, реализовать ручное, автоматическое с 30 минутами
}