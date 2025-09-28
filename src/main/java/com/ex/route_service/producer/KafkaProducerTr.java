package com.ex.route_service.producer;

import org.springframework.stereotype.Service;

@Service
public class KafkaProducerTr {

//    private final KafkaTemplate<String, String> kafkaTrTemplate;
//    private final ObjectMapper objectMapper;

//    public KafkaProducerTr(@Qualifier("kafkaTrTemplate") KafkaTemplate<String, String> kafkaTrTemplate, ObjectMapper objectMapper) {
//        this.kafkaTrTemplate = kafkaTrTemplate;
//        this.objectMapper = objectMapper;
//    }

//    отправляем сообщения в два топика, транзакционность обеспечивается на стороне продусера.
//    В данном методе, если отправится первое сообщение, а второе нет,
//    то первое будет невидно для консумеров. Работает за счет @Transactional("transactionManager")
//    todo !!! эта штука ломает транзакции jpa. Ничего в базу сохранится не может.!!!
//    @Transactional("transactionManager")
//    public void sendMessage(String topic, SendRouteEventsRequestDto.RouteEventDto dto) throws JsonProcessingException, InterruptedException {
//
//        String json = objectMapper.writeValueAsString(dto);
//
//        kafkaTrTemplate.send(topic, json);
//        System.out.println("some wait");
//        Thread.sleep(10000); // проверяем транзакцию
//        kafkaTrTemplate.send(topic, json);
//
//        System.out.println("Sent message: " + dto);
//    }
}
