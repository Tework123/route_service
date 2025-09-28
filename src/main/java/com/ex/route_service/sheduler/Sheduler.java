package com.ex.route_service.sheduler;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.producer.FanoutRabbitProducer;
import com.ex.route_service.service.RouteEventDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Sheduler {
    private final RouteEventDataService routeEventDataService;
    private final FanoutRabbitProducer fanoutRabbitProducer;

//    fanout отправка во все очереди, которые имеют установленный exchange
//     сделать еще один сервис, проверить что в его очередь тоже отправляется
//     провести стресс тест, кучу сообщений.
//    синххронная отправка, асинхронная и тд, почитать на хабре, у gpt, посмотреть кейсы реальные
//    @Scheduled(fixedRate = 6000) // каждые 6 секунд
    public void sendRouteEvents() throws JsonProcessingException {
        List<SendRouteEventsRequestDto.RouteEventDto> routeEventDtos = routeEventDataService.getAllRouteEvents();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);
        log.info("Начинается ежедневная отправка ивентов, время начала - {}, количество - {}", formattedTime, routeEventDtos.size());
        for (int i = 0; i < routeEventDtos.size(); i++) {
            fanoutRabbitProducer.sendRouteEvents(routeEventDtos.get(i));
        }

    }
}
