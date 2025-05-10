package com.ex.route_service.mapper;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.CreateLocationRequestDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.GetLastLocationPointDto;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.entity.WorkShiftSession;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class LocationPointMapper {
    public GetLastLocationPointDto toResponseDto(LocationPoint locationPoint) {
        if (locationPoint == null) return null;

        GetLastLocationPointDto dto = new GetLastLocationPointDto();
        dto.setLatitude(locationPoint.getLocation().getY());  // Latitude
        dto.setLongitude(locationPoint.getLocation().getX()); // Longitude
//        dto.setLatitude(dto.getLatitude());
//        dto.setLongitude(dto.getLongitude());
        dto.setDeviceTimestamp(dto.getDeviceTimestamp());
        return dto;
    }

    public LocationPoint toEntity(CreateLocationRequestDto dto, WorkShiftSession workShiftSession) {
        if (dto == null) return null;

//        вынести сверху
        GeometryFactory geometryFactory = new GeometryFactory();
        //        преобразование double Longitude и Latitude в Point
        Coordinate coordinate = new Coordinate(dto.getLongitude(), dto.getLatitude());
        Point point = geometryFactory.createPoint(coordinate);

        return LocationPoint.builder()
                .workShiftSession(workShiftSession)
                .location(point)
                .timestamp(dto.getTimestamp())
                .build();
    }

}
