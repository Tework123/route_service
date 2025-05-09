package com.ex.route_service.controller;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.CreateLocationRequestDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.GetLastLocationPointDto;
import com.ex.route_service.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Контроллер для приёма координатных данных от устройств.
 * <p>
 * Ожидает POST-запросы по адресу {@code /location}, содержащие в теле JSON с координатами и временем.
 * Данные сохраняются через сервис {@link LocationService}.
 */
@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    /**
     * Принимает координаты устройства и сохраняет их в систему.
     *
     * @param request объект запроса, содержащий координаты и время отправки
     * @return HTTP-ответ 200 OK в случае успешного сохранения
     */
    @PostMapping
    public ResponseEntity<Void> createLocationPoint(@RequestBody CreateLocationRequestDto request) {
        locationService.save(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/last/{deviceId}")
    public GetLastLocationPointDto getLastLocationPoint(@PathVariable UUID deviceId) {
        return locationService.getLast(deviceId);
    }
//   придумать логику, несколько сложных сервисов с координатами, потом миграции:
//    как выводить инфу, где новый маршрут начинается
//     javadoc
//    TODO Получение маршрута устройства(сделать завтра), сделал записи в гугл,
//     норм логика, реализовать ручное, автоматическое с 30 минутами
}