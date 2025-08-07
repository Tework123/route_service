package com.ex.route_service.producer;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.ex.route_service.config.RabbitConfig.EXCHANGE_FANOUT;

@Log4j2
@Component
@RequiredArgsConstructor
public class FanoutRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendRouteEvents(SendRouteEventsRequestDto.RouteEventDto requestDto) {
        log.info("Отправляется ивент с телом id - {}", requestDto.getRouteEventId());
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT, "", requestDto);
        log.info("Ивент отправился id - {}", requestDto.getRouteEventId());

    }
}
