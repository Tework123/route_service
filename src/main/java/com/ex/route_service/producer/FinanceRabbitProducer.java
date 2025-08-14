package com.ex.route_service.producer;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.ex.route_service.config.RabbitConfig.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FinanceRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendRouteEvents(SendRouteEventsRequestDto requestDto) {
        log.info("Сообщение пытается отправиться в очередь");
//       логи срабатывают, вроде пришло
        rabbitTemplate.convertAndSend(ROUTE_EVENTS_EXCHANGE, ROUTE_EVENTS_QUEUE_ROUTING_KEY, requestDto);
        log.info("Сообщение отправлено в очередь, ROUTE_EVENTS_QUEUE_ROUTING_KEY. Тело: {}", requestDto);
    }
}