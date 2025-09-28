package com.ex.route_service.service;


import com.ex.route_service.dto.RouteServiceDto.CountDto;
import com.ex.route_service.entity.Count;
import com.ex.route_service.enums.CountStatus;
import com.ex.route_service.mapper.CountMapper;
import com.ex.route_service.repository.CountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountService {
    private final CountRepository countRepository;

    @Transactional
    public CountDto create() {
        Count newCount = Count.builder()
                .count(1)
                .countStatus(CountStatus.NEW)
                .routeEventId(UUID.randomUUID())
                .build();

        Count count = countRepository.save(newCount);
        log.info("Route_service добавил count в бд. id = {}, date = {}", count.getId(), newCount.getCreateDt());
        return CountMapper.toDto(count);
    }

//    public void cancel(UUID routeEventId) {
//        Count count = countRepository.findByRouteEventId(routeEventId);
//
//        if (count != null) {
//            count.setCountStatus(CountStatus.CANCEL);
//            countRepository.save(count);
//            log.info("Finance_service отменил count в бд  {}", routeEventId);
//        }
//        log.info("Finance_service не нашел count в бд чтобы его отменить  {}", routeEventId);
//
//    }
}
