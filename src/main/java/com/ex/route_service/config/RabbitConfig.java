package com.ex.route_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    public static final String ROUTE_EVENTS_QUEUE = "route_events_queue";
    public static final String EMAIL_QUEUE = "email.queue";

    public static final String ROUTE_EVENTS_EXCHANGE = "route_events_exchange";

    public static final String EXCHANGE_FANOUT = "fanout";

    public static final String ROUTE_EVENTS_QUEUE_ROUTING_KEY = "route_events_queue.routingKey";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        log.warn("✅ Message acknowledged by broker, correlationId={}");
        System.out.println(123123);

//        если долетело до очереди, до зеленый, если нет, пишем ошибку в лог
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.warn("✅ Message acknowledged by broker, correlationId={}", correlationData != null ? correlationData.getId() : "null");
            } else {
                log.warn("❌ Message NOT acknowledged! correlationId={}, cause={}", correlationData != null ? correlationData.getId() : "null", cause);
            }
        });

//        если не нашел очередь для сообщения, то не выбросит его, а вернет.
        template.setMandatory(true);
        template.setReturnsCallback(returned -> {
            log.warn("Не нашел очередь для сообщения!: {}", returned.getMessage());
            log.warn("Returned message: {}", returned.getMessage());
            log.warn("Exchange: {}", returned.getExchange());
            log.warn("Routing key: {}", returned.getRoutingKey());

        });
        return template;
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED); // Включаем confirms
        factory.setPublisherReturns(true); // для returned messages
        return factory;
    }
}