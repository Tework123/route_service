package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.courierDto.GetCouriersForOrderResponseDto;
import com.ex.route_service.dto.RouteServiceDto.routeEventDto.StartWorkRequestDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.CourierRouteEvent;
import com.ex.route_service.enums.CourierRouteEventStatus;
import com.ex.route_service.enums.CourierStatus;
import com.ex.route_service.enums.TransportType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CourierMapper {

//    public CourierWithDistanceDto mapToDto(Map<String, Object> row) {
//        return new CourierWithDistanceDto(
//                UUID.fromString((String) row.get("courier_id")),
//                (String) row.get("transport_type"),
//                ((Number) row.get("latitude")).doubleValue(),
//                ((Number) row.get("longitude")).doubleValue(),
//                ((Number) row.get("total_distance")).doubleValue()
//        );
//    }

    public GetCouriersForOrderResponseDto toResponseDto(Map<String, Object> row, UUID orderId) {
        return GetCouriersForOrderResponseDto.builder()
                .courierId(UUID.fromString((String) row.get("courier_id")))
                .transportType(TransportType.valueOf((String) row.get("transport_type")))
                .distance(((Number) row.get("total_distance")).doubleValue())
                .courierStatus(CourierStatus.READY) // можно подтягивать из row, если добавишь
                .currentLocation(
                        GetCouriersForOrderResponseDto.LocationDto.builder()
                                .longitude(((Number) row.get("longitude")).doubleValue())
                                .latitude(((Number) row.get("latitude")).doubleValue())
                                .build()
                )
                .orderId(orderId)
                .build();
    }


    public List<GetCouriersForOrderResponseDto> toListResponseDto(List<Map<String, Object>> rowCouriers, UUID orderId) {
        return rowCouriers.stream()
                .map(row -> toResponseDto(row, orderId))
                .collect(Collectors.toList());
    }

//    public GetCouriersForOrderResponseDto toResponseDto(Courier courier) {
//        if (courier == null) return null;
//
//        GetCouriersForOrderResponseDto.LocationDto locationDto = new GetCouriersForOrderResponseDto
//                .LocationDto(courier.getCurrentLocation().getX(), courier.getCurrentLocation().getY());
//
//        return GetCouriersForOrderResponseDto.builder()
//                .courierId(courier.getCourierId())
//                .currentLocation(locationDto)
//                .courierStatus(courier.getCourierStatus())
//                .transportType(courier.getTransportType())
//                .build();
//    }

    public CourierRouteEvent toEntity(StartWorkRequestDto dto, CourierRouteEventStatus status, Courier courier) {
        if (dto == null) return null;

// надо в отдельный сервис вынести
        GeometryFactory geometryFactory = new GeometryFactory();
        //        преобразование double Longitude и Latitude в Point
        Coordinate coordinate = new Coordinate(dto.getLongitude(), dto.getLatitude());
        Point point = geometryFactory.createPoint(coordinate);

        return CourierRouteEvent.builder()
                .courier(courier)
                .location(point)
                .courierRouteEventStatus(status)
                .timestamp(dto.getTimestamp())
                .build();
    }
}


