package com.ex.route_service.producer;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.ex.route_service.config.RabbitConfig.EXCHANGE_NAME;
import static com.ex.route_service.config.RabbitConfig.ROUTE_EVENTS_QUEUE_ROUTING_KEY;

@Log4j2
@Component
@RequiredArgsConstructor
public class FanoutRabbitProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendRouteEvents(SendRouteEventsRequestDto.RouteEventDto requestDto) throws JsonProcessingException {
        log.info("Отправляется ивент с телом id - {}", requestDto.getRouteEventId());
//        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT, "", requestDto);

        String json = objectMapper.writeValueAsString(requestDto);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTE_EVENTS_QUEUE_ROUTING_KEY, json, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        log.info("Ивент отправился id - {}", requestDto.getRouteEventId());

    }
}
