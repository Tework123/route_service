package com.ex.route_service.service;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationResponseDto;
import com.ex.route_service.entity.Courier;
import com.ex.route_service.entity.LocationPoint;
import com.ex.route_service.mapper.LocationPointMapper;
import com.ex.route_service.repository.CourierRepository;
import com.ex.route_service.repository.LocationPointRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final CourierRepository courierRepository;
    private final RedisService redisService;


    //    работает со сменой работника(с телефона отправляется). Как будто надо еще для транспорта сделать
//    ручку, когда транспорт не используется, чтобы можно было бы следить за его местоположением(мейби)
    public LocationPoint save(LocationDto locationDto, UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationPoint locationPoint = LocationPointMapper.toEntity(locationDto, courier, null);
        LocationPoint locationPointFromDb = locationPointRepository.save(locationPoint);

//todo проверить кеширование, обновлять кеш, когда меняется локация и другой функционал
//        кешируем текущую локацию по id курьера
        redisService.save("courier:" + courier.getCourierId() + ":location", locationDto);
//вроде работает

        LocationDto location = redisService.get("courier:" + courierId + ":location", LocationDto.class);
        System.out.println(location);
        return locationPoint;
    }


    public LocationResponseDto getLastLocationPoint(UUID courierId) {
        courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        LocationPoint locationPoint = locationPointRepository.findTopByCourierId(courierId);
        return LocationPointMapper.toLocationResponseDtoFromEntity(locationPoint);
    }

    public List<LocationResponseDto> getLocationPoints(UUID courierId,
                                                       LocalDateTime fromDateTime,
                                                       LocalDateTime toDateTime) {
        courierRepository.findById(courierId).orElseThrow(()
                -> new EntityNotFoundException("Курьер не найден: " + courierId));

        List<LocationPoint> locationPoints = locationPointRepository.findByCourierIdAndTimestampBetween(courierId, fromDateTime, toDateTime);
        return LocationPointMapper.toLocationResponseDtoFromEntity(locationPoints);
    }

}