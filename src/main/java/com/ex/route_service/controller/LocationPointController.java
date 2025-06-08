package com.ex.route_service.controller;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.service.LocationPointService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для работы с точками локации курьера.
 */
@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationPointController {

    private final LocationPointService locationPointService;

    /**
     * Получает последнюю сохранённую точку локации для указанного курьера.
     *
     * @param courierId UUID курьера
     * @return DTO с последней точкой локации
     */
    @GetMapping("/{courierId}")
    public LocationDto getLastLocationPoint(@PathVariable UUID courierId) {
        return locationPointService.getLastLocationPoint(courierId);
    }

    /**
     * Получает список точек локации курьера за указанный временной промежуток.
     *
     * @param courierId    UUID курьера
     * @param fromDateTime параметр начала временного диапазона
     * @param toDateTime   параметр конца временного диапазона
     * @return список DTO с точками локации за период времени
     */
    @GetMapping("/{courierId}/points")
    public List<LocationDto> getLocationPoints(
            @PathVariable UUID courierId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDateTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDateTime
    ) {
        return locationPointService.getLocationPoints(courierId, fromDateTime, toDateTime);
    }

    /**
     * Принимает координаты устройства курьера и сохраняет их в систему.
     *
     * @param request   DTO с координатами и временем отправки
     * @param courierId UUID курьера
     * @return HTTP 200 OK при успешном сохранении
     */
    @PostMapping("/{courierId}")
    public ResponseEntity<Void> createLocationPoint(@RequestBody LocationDto request,
                                                    @PathVariable UUID courierId) {
        locationPointService.save(request, courierId);
        return ResponseEntity.ok().build();

    }
}