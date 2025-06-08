package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCourierResponseDto;
import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.TransportType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CourierMapper {

    public List<GetCouriersForOrderResponseDto> toListResponseDto(List<Map<String, Object>> rowCouriers, UUID orderId) {
        return rowCouriers.stream()
                .map(row -> toResponseDto(row, orderId))
                .collect(Collectors.toList());
    }

    public GetCouriersForOrderResponseDto toResponseDto(Map<String, Object> row, UUID orderId) {
        return GetCouriersForOrderResponseDto.builder()
                .courierId((UUID) row.get("courier_id"))
                .transportType(TransportType.valueOf((String) row.get("transport_type")))
                .distance(((Number) row.get("total_distance")).doubleValue())
                .courierStatus(CourierStatus.READY)
                .currentLocation(LocationDto.builder()
                                .longitude(((Number) row.get("longitude")).doubleValue())
                                .latitude(((Number) row.get("latitude")).doubleValue())
                                .build()
                )
                .orderId(orderId)
                .build();
    }

    public GetCourierResponseDto toResponseDto(Courier courier, LocationDto locationDto) {
        if (courier == null) return null;

        return GetCourierResponseDto.builder()
                .courierId(courier.getCourierId())
                .currentLocation(locationDto)
                .courierStatus(courier.getCourierStatus())
                .transportType(courier.getTransportType())
                .build();
    }

}


