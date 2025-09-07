package com.ex.route_service.sheduler;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.route_service.producer.KafkaProducer;
import com.ex.route_service.producer.KafkaProducerTr;
import com.ex.route_service.producer.KafkaSagaProducer;
import com.ex.route_service.service.RouteEventDataService;
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
    private final KafkaProducerTr kafkaProducerTr;
    private final KafkaSagaProducer kafkaSagaProducer;


    //    стандарт
    //    @Scheduled(fixedRate = 6000) // каждые 6 секунд
    public void sendRouteEvents() throws Exception {
        List<SendRouteEventsRequestDto.RouteEventDto> routeEventDtos = routeEventDataService.getAllRouteEvents();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);
        log.info("Начинается ежедневная отправка ивентов, время начала - {}, количество - {}", formattedTime, routeEventDtos.size());
        for (int i = 0; i < routeEventDtos.size(); i++) {
            kafkaProducer.sendMessage("my-topic", routeEventDtos.get(i));
//            kafkaProducer.sendAndReceive("request-topic", routeEventDtos.get(i));
        }
    }

    //     транзакция на стороне продусера, без повторов
//    @Scheduled(fixedRate = 6000) // каждые 6 секунд
    public void sendRouteEventsTr() throws Exception {
        List<SendRouteEventsRequestDto.RouteEventDto> routeEventDtos = routeEventDataService.getAllRouteEvents();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);
        log.info("Начинается ежедневная отправка ивентов тр, время начала - {}, количество - {}", formattedTime, routeEventDtos.size());
        for (int i = 0; i < routeEventDtos.size(); i++) {
            kafkaProducerTr.sendMessage("tr-topic", routeEventDtos.get(i));
        }
    }

    // допустим такой флоу: route_service достает из базы данные, отправляет по кафке в finance_service данные,
    // также отправляет в order_service те же данные.
//    finance_service пишет в бд, потом отправляет сообщение в топик подтверждения выполнения без ошибок.
//    order_service делает тоже самое.
//    если оба сообщения в топик без ошибок получены, то транзакция комитится
//    если один сервис падает с ошибкой, то он откатывает свою бд и отправляет сообщение в топик ошибок.
    // другой сервис видит это и откатывает свои изменения, потом надо еще отправить сообщение в
    // продусер об ошибке(типо тоже откатим что-то)
    // тот самый saga
    @Scheduled(fixedRate = 6000) // каждые 6 секунд
    public void sendRouteEventsSaga() throws Exception {
        List<SendRouteEventsRequestDto.RouteEventDto> routeEventDtos = routeEventDataService.getAllRouteEvents();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);
        log.info("Начинается ежедневная отправка ивентов saga, время начала - {}, количество - {}", formattedTime, routeEventDtos.size());
//        for (int i = 0; i < routeEventDtos.size(); i++) {
        kafkaSagaProducer.sendMessage("saga-main-topic", routeEventDtos.get(0));
//        }
    }
}
