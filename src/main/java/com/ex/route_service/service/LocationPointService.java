package com.ex.route_service.service;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.mapper.LocationPointMapper;
import com.ex.route_service.repository.CourierRepository;
import com.ex.route_service.repository.LocationPointRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для обработки координатных данных от устройств.
 */
@Service
@RequiredArgsConstructor
public class LocationPointService {

    private final LocationPointRepository locationPointRepository;
    private final CourierRepository courierRepository;
    private final RedisService redisService;


    /**
     * Сохраняет координаты устройства для заданного курьера.
     *
     * @param locationDto координаты и данные устройства
     * @param courierId UUID курьера
     * @return сохранённый объект LocationPoint
     */
    public LocationPoint save(LocationDto locationDto, UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationPoint locationPoint = LocationPointMapper.toEntity(locationDto, courier, null);

        redisService.save("courier:" + courier.getCourierId() + ":location", locationDto, Duration.ofMinutes(2));
        return locationPoint;
    }

    /**
     * Получает последнюю сохранённую координату курьера.
     *
     * @param courierId UUID курьера
     * @return координаты последней точки
     */
    public LocationDto getLastLocationPoint(UUID courierId) {
        courierRepository.findById(courierId)
                .orElseThrow(() -> new EntityNotFoundException("Курьер не найден: " + courierId));

        String cacheKey = "courier:" + courierId + ":location";
        LocationDto cached = redisService.get(cacheKey, LocationDto.class);
        if (cached != null) {
            return cached;
        }

        LocationPoint locationPoint = locationPointRepository.findTopByCourierId(courierId);
        if (locationPoint == null) {
            throw new EntityNotFoundException("Локация для курьера не найдена: " + courierId);
        }

        LocationDto locationDto = LocationPointMapper.toDtoFromEntity(locationPoint);
        redisService.save(cacheKey, locationDto, Duration.ofMinutes(2));
        return locationDto;
    }

    /**
     * Получает список координат курьера за заданный промежуток времени.
     *
     * @param courierId UUID курьера
     * @param fromDateTime начало интервала
     * @param toDateTime конец интервала
     * @return список координат
     */
    public List<LocationDto> getLocationPoints(UUID courierId,
                                                       LocalDateTime fromDateTime,
                                                       LocalDateTime toDateTime) {
        courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        List<LocationPoint> locationPoints = locationPointRepository.findByCourierIdAndTimestampBetween(courierId, fromDateTime, toDateTime);
        return LocationPointMapper.toLocationDtoFromEntity(locationPoints);
    }

}