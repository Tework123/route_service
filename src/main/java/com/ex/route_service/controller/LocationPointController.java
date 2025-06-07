package com.ex.route_service.controller;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationResponseDto;
import com.ex.route_service.service.LocationPointService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер для приёма координатных данных от устройств.
 */
@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationPointController {

    private final LocationPointService locationPointService;

    /**
     * Принимает координаты устройства и сохраняет их в систему.
     *
     * @param request объект запроса, содержащий координаты и время отправки
     * @return HTTP-ответ 200 OK в случае успешного сохранения
     */
    @PostMapping("/{courierId}")
    public ResponseEntity<Void> createLocationPoint(@RequestBody LocationDto request,
                                                    @PathVariable UUID courierId) {
        locationPointService.save(request, courierId);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{courierId}")
    public LocationResponseDto getLastLocationPoint(@PathVariable UUID courierId) {
        return locationPointService.getLastLocationPoint(courierId);
    }

    //    добавить параметры поиска: промежутки времени.
    @GetMapping("/{courierId}/points")
    public List<LocationResponseDto> getLocationPoints(
            @PathVariable UUID courierId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDateTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDateTime
    ) {
        return locationPointService.getLocationPoints(courierId, fromDateTime, toDateTime);
    }
}