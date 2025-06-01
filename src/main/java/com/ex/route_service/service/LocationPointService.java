package com.ex.route_service.service;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.mapper.LocationPointMapper;
import com.ex.route_service.repository.CourierRepository;
import com.ex.route_service.repository.LocationPointRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для обработки координатных данных от устройств.
 * <p>
 * Отвечает за преобразование входных данных и сохранение их в базу данных.
 */
@Service
@RequiredArgsConstructor
public class LocationPointService {

    private final LocationPointRepository locationPointRepository;
    private final LocationPointMapper locationPointMapper;
    private final CourierRepository courierRepository;


    //    работает со сменой работника(с телефона отправляется). Как будто надо еще для транспорта сделать
//    ручку, когда транспорт не используется, чтобы можно было бы следить за его местоположением(мейби)
    public LocationPoint save(LocationDto locationDto, UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationPoint locationPoint = locationPointMapper.toEntity(locationDto, courier, null);
        return locationPointRepository.save(locationPoint);
    }

    public LocationPoint getLastLocationPoint(UUID courierId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<LocationPoint> latestLocationPoint = locationPointRepository.findLatestByCourierId(courierId, pageable);
        return latestLocationPoint.isEmpty() ? null : latestLocationPoint.getFirst();
    }

}