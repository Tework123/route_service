//package com.ex.route_service.controller;
//
//import com.ex.route_service.dto.RouteServiceDto.vehicleDto.CreateVehicleRequestDto;
//import com.ex.route_service.dto.RouteServiceDto.vehicleDto.GetVehicleResponseDto;
//import com.ex.route_service.dto.RouteServiceDto.vehicleDto.UpdateVehicleDto;
//import com.ex.route_service.service.VehicleService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/vehicle")
//@AllArgsConstructor
//public class VehicleController {
//
//    private final VehicleService vehicleService;
//
//
//    @PostMapping
//    public ResponseEntity<Void> createDevice(@RequestBody CreateVehicleRequestDto createVehicleRequestDto) {
//        vehicleService.save(createVehicleRequestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("/{vehicleId}")
//    public GetVehicleResponseDto getVehicle(@PathVariable UUID vehicleId) {
//        return vehicleService.getVehicleById(vehicleId);
//
//    }
//
//    @PutMapping("/{vehicleId}")
//    public ResponseEntity<Void> updateVehicle(@PathVariable UUID vehicleId, @RequestBody UpdateVehicleDto dto) {
//        vehicleService.update(vehicleId, dto);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{vehicleId}")
//    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID vehicleId) {
//        vehicleService.delete(vehicleId);
//        return ResponseEntity.ok().build();
//    }
//
//
//}
