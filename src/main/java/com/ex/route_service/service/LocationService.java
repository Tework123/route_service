package com.ex.route_service.service;

import com.ex.route_service.dto.RouteServiceDto.locationPointDto.CreateLocationRequestDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.GetLastLocationPointDto;
import com.ex.route_service.entity.LocationPoint;

import com.ex.route_service.entity.WorkShiftSession;
import com.ex.route_service.mapper.LocationPointMapper;
import com.ex.route_service.repository.LocationPointRepository;
import com.ex.route_service.repository.WorkShiftSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Сервис для обработки координатных данных от устройств.
 * <p>
 * Отвечает за преобразование входных данных и сохранение их в базу данных.
 */
@Service
@AllArgsConstructor
public class LocationService {

    private final LocationPointRepository locationPointRepository;
    private final WorkShiftSessionRepository workShiftSessionRepository;
    private final LocationPointMapper locationPointMapper;

//    работает со сменой работника(с телефона отправляется). Как будто надо еще для транспорта сделать
//    ручку, когда транспорт не используется, чтобы можно было бы следить за его местоположением(мейби)
    @Transactional
    public void save(CreateLocationRequestDto dto) {
        WorkShiftSession workShiftSession = workShiftSessionRepository.findById(dto.getWorkShiftSessionId())
                .orElseThrow(() -> new EntityNotFoundException("session not found with id: " + dto.getWorkShiftSessionId()));

        LocationPoint locationPoint = locationPointMapper.toEntity(dto, workShiftSession);
        locationPointRepository.save(locationPoint);
    }

    public GetLastLocationPointDto getLast(UUID workShiftSessionId) {
        if (!workShiftSessionRepository.existsById(workShiftSessionId)) {
            throw new EntityNotFoundException("D: " + workShiftSessionId);
        }

        LocationPoint locationPoint = locationPointRepository.findLastByWorkShiftSessionId(workShiftSessionId);
        return locationPointMapper.toResponseDto(locationPoint);

    }

}