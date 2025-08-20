package com.ex.route_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic myTopic() {
        return new NewTopic("my-topic-2", 3, (short) 1)
                .configs(Map.of(
                        "retention.ms", "3600000",
                        "cleanup.policy", "delete",
                        "max.message.bytes", "1048576"
                ));
    }

    // Топик для запросов
    @Bean
    public NewTopic requestTopic() {
        return new NewTopic("request-topic", 1, (short) 1);
    }

    // Топик для ответов
    @Bean
    public NewTopic replyTopic() {
        return new NewTopic("reply-topic", 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        DefaultKafkaProducerFactory<String, String> factory =
                new DefaultKafkaProducerFactory<>(producerConfigs());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Контейнер для listener'а, который будет обрабатывать ответы
    @Bean
    public ConcurrentMessageListenerContainer<String, String> replyContainer(
            org.springframework.kafka.core.ConsumerFactory<String, String> consumerFactory) {

        ContainerProperties containerProps = new ContainerProperties("reply-topic");
        containerProps.setGroupId("my-reply-group"); // <- здесь задаём group.id

        return new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        // transactional.id генерируется с префиксом my-tx-
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerReplyingFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    // ReplyingKafkaTemplate
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
            org.springframework.kafka.core.ProducerFactory<String, String> producerReplyingFactory,
            ConcurrentMessageListenerContainer<String, String> replyContainer) {

        return new ReplyingKafkaTemplate<>(producerReplyingFactory, replyContainer);
    }
}