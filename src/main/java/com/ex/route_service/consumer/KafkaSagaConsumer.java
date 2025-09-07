package com.ex.route_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSagaConsumer {
    private final ObjectMapper objectMapper;

//    todo нужен консумер здесь, чтобы откатывать здесь базу, пока не сделал count.

//    @KafkaListener(topics = "saga-main-topic",
//            groupId = "route_service_main_consumer",
//            containerFactory = "kafkaSagaMainListenerContainerFactory")
//    public void listenSagaMainTopic(String message) throws JsonProcessingException {
//        SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(message, SendRouteEventsRequestDto.RouteEventDto.class);
//        System.out.println("financeService consumer принял сообщение из saga-main-topic: " + dto.getRouteEventId());
//// пишем в базу
////        отправляем в другой топик сообщение
////        если exception то в другой топик, а если при отправке туда тоже exception? gg
//    }

    public void listenSagaSuccessTopic(){

    }
}
