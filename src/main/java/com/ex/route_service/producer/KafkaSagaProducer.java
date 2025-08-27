package com.ex.route_service.producer;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSagaProducer {
    private final KafkaTemplate<String, String> kafkaSagaMainTemplate;
    private final ObjectMapper objectMapper;

    public KafkaSagaProducer(@Qualifier("kafkaSagaMainTemplate") KafkaTemplate<String, String> kafkaSagaMainTemplate, ObjectMapper objectMapper) {
        this.kafkaSagaMainTemplate = kafkaSagaMainTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String topic, SendRouteEventsRequestDto.RouteEventDto dto) throws JsonProcessingException, InterruptedException {
        String json = objectMapper.writeValueAsString(dto);

        kafkaSagaMainTemplate.send(topic, json);

        System.out.println("Route_service producer отправил сообщение в saga-main-topic: " + dto);
    }
}
