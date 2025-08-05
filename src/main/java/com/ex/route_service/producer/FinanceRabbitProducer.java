package com.ex.route_service.producer;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.ex.route_service.config.RabbitConfig.*;

@Service
@Slf4j
public class FinanceRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public FinanceRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

//    ебануть шедулер, пусть пуляет кучу сообщений
    public void sendRouteEvents(SendRouteEventsRequestDto requestDto) {
        log.info("Сообщение пытается отправиться в очередь");
//       логи срабатывают, вроде пришло
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTE_EVENTS_QUEUE_ROUTING_KEY, requestDto);
        log.info("Сообщение отправлено в очередь");
    }
}