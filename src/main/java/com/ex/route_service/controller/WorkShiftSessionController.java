package com.ex.route_service.controller;


import com.ex.route_service.dto.OrderServiceDto.GetWorkShiftSessionsDto;
import com.ex.route_service.dto.RouteServiceDto.workShiftSessionDto.CreateWorkShiftSessionDto;
import com.ex.route_service.service.WorkShiftSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/work_shift_session")
@AllArgsConstructor
public class WorkShiftSessionController {

    private final WorkShiftSessionService workShiftSessionService;


    //   для сервиса заказов, на выборку ближайших курьеров, метод должен получить координаты ресторана и места доставки.
//    отдает рабочие сессии с ready и координаты курьеров, очевидно это не должен быть контроллер, пока так
    @GetMapping("/users")
    public ResponseEntity<Void> getAllWorkShiftSessionsByCoordinates(@RequestBody GetWorkShiftSessionsDto request) {
        workShiftSessionService.getAllWorkShiftSessionsByCoordinates(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start/{userId}")
    public ResponseEntity<Void> createWorkShiftSession(@RequestBody CreateWorkShiftSessionDto request,
                                                       @PathVariable UUID userId) {
        workShiftSessionService.create(request, userId);
        return ResponseEntity.ok().build();
    }
}
