package com.ex.route_service.sheduler;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.producer.KafkaProducer;
import com.ex.route_service.service.RouteEventDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShedulerKafka {
    private final RouteEventDataService routeEventDataService;
    private final KafkaProducer kafkaProducer;


    @Scheduled(fixedRate = 6000) // каждые 6 секунд
    public void sendRouteEvents() throws JsonProcessingException {
        List<SendRouteEventsRequestDto.RouteEventDto> routeEventDtos = routeEventDataService.getAllRouteEvents();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);
        log.info("Начинается ежедневная отправка ивентов, время начала - {}, количество - {}", formattedTime, routeEventDtos.size());
        for (int i = 0; i < routeEventDtos.size(); i++) {
            kafkaProducer.sendMessage("my-topic", routeEventDtos.get(i));

        }

    }
}
