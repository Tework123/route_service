package com.ex.route_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    public static final String ROUTE_EVENTS_QUEUE = "route_events_queue";
    public static final String EMAIL_QUEUE = "email.queue";

    public static final String EXCHANGE_NAME = "delivery";

    public static final String EXCHANGE_FANOUT = "fanout";


    public static final String ROUTE_EVENTS_QUEUE_ROUTING_KEY = "route_events_queue.routingKey";
    public static final String EMAIL_ROUTING_KEY = "email.routingKey";
    public static final String EMPTY_ROUTING_KEY = "empty.routingKey";


//    @Bean
//    public Queue mainQueue() {
//        return new Queue(ROUTE_EVENTS_QUEUE, true);
//    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }

//    @Bean
//    public Binding mainBinding(Queue mainQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(mainQueue).to(exchange).with(ROUTE_EVENTS_QUEUE_ROUTING_KEY);
//    }
//
//    @Bean
//    public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(emailQueue).to(exchange).with(EMAIL_ROUTING_KEY);
//    }

//    @Bean
//    public Binding emptyBinding(Queue emailQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(emailQueue).to(exchange).with(EMPTY_ROUTING_KEY);
//    }

    @Bean
    public FanoutExchange deliveryExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.warn("✅ Message acknowledged by broker, correlationId={}", correlationData != null ? correlationData.getId() : "null");
            } else {
                log.warn("❌ Message NOT acknowledged! correlationId={}, cause={}", correlationData != null ? correlationData.getId() : "null", cause);
            }
        });

        template.setMandatory(true);
        template.setReturnsCallback(returned -> {
            log.warn("Returned message: {}", returned.getMessage());
            log.info("Exchange: {}", returned.getExchange());
            log.warn("Routing key: {}", returned.getRoutingKey());
            System.out.println("Sending with routing key: " + returned.getRoutingKey());

        });
        return template;
    }
}