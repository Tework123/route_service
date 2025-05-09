package com.ex.route_service.service;

import com.ex.route_service.dto.RouteServiceDto.workShiftSessionDto.CreateWorkShiftSessionDto;
import com.ex.route_service.entity.WorkShiftSession;
import com.ex.route_service.enums.WorkShiftSessionStatus;
import com.ex.route_service.mapper.WorkShiftSessionMapper;
import com.ex.route_service.repository.WorkShiftSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkShiftSessionService {
    private final WorkShiftSessionRepository workShiftSessionRepository;
    private final WorkShiftSessionMapper workShiftSessionMapper;

    // курьер начинает рабочую смену, добавляется запись о новой смене в бд
    @Transactional
    public void create(CreateWorkShiftSessionDto dto, UUID userId) {

//         пытаемся у этого курьера найти активную рабочую сессию, если нет, создаем новую
        List<WorkShiftSession> workShiftSessionList = workShiftSessionRepository.findAllByUserIdAndStatusNotAborted(userId);
        if (!workShiftSessionList.isEmpty()) {
            throw new EntityNotFoundException("Найдена активная смена для курьера с id: " + userId);
        }

        WorkShiftSession newWorkShiftSession = workShiftSessionMapper.toEntity(dto, WorkShiftSessionStatus.READY, userId);
        workShiftSessionRepository.save(newWorkShiftSession);

    }


    //    должен возвращать list активных рабочих смен,
    //    надо фильтровать по статус смены и координатам(в каком то пределе,
    //    допустим пару километров от места доставки и места получения заказа, спросить у gpt)
    public void getAllWorkShiftSessionsByCoordinates(CreateWorkShiftSessionDto dto, UUID userId) {

//         пытаемся у этого курьера найти активную рабочую сессию, если нет, создаем новую
        List<WorkShiftSession> workShiftSessionList = workShiftSessionRepository.findAllByUserIdAndStatusNotAborted(userId);
        if (!workShiftSessionList.isEmpty()) {
            throw new EntityNotFoundException("Найдена активная смена для курьера с id: " + userId);
        }

        WorkShiftSession newWorkShiftSession = workShiftSessionMapper.toEntity(dto, WorkShiftSessionStatus.READY, userId);
        workShiftSessionRepository.save(newWorkShiftSession);

    }
}
