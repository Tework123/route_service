package com.ex.route_service.consumer;

import com.ex.route_service.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = RabbitConfig.ROUTE_EVENTS_QUEUE)
    public void receiveMessage(String message) {
        System.out.println("Получено сообщение: " + message);
    }
}