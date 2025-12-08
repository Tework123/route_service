package com.ex.route_service.consumer;

import com.ex.route_service.service.CountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaSagaConsumer {
    private final ObjectMapper objectMapper;
    private final CountService countService;

    @KafkaListener(topics = "saga-cancel-topic",
            groupId = "route_service_cancel_consumer",
            containerFactory = "kafkaSagaCancelListenerContainerFactory")
    public void listenSagaCancelTopic(String message) throws JsonProcessingException {
        UUID routeEventId = objectMapper.readValue(message, UUID.class);
        System.out.println("route_service consumer принял сообщение из saga-cancel-topic: " + routeEventId);
        countService.cancel(routeEventId);
    }
}
