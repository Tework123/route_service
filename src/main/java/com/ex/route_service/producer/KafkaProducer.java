package com.ex.route_service.producer;

import com.ex.route_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ReplyingKafkaTemplate<String, String, String> replyingTemplate;

    public KafkaProducer(@Qualifier("kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
                         ObjectMapper objectMapper,
                         ReplyingKafkaTemplate<String, String, String> replyingTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.replyingTemplate = replyingTemplate;
    }

    public void sendMessage(String topic, SendRouteEventsRequestDto.RouteEventDto dto) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(dto);

//в одну партицию будет спамить, чтобы порядок сохранялся
//        kafkaTemplate.send(topic, "some_key_123", json);
        kafkaTemplate.send(topic, json);

        System.out.println("Sent message: " + dto);
    }

    public void sendAndReceive(String topic, SendRouteEventsRequestDto.RouteEventDto dto) throws Exception {
        String json = objectMapper.writeValueAsString(dto);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);

        // отправка и получение ответа
        RequestReplyFuture<String, String, String> future = replyingTemplate.sendAndReceive(record);

        // ждем ответ синхронно
        String response = future.get(10, java.util.concurrent.TimeUnit.SECONDS).value();
        System.out.println("Response: " + response);
    }

}