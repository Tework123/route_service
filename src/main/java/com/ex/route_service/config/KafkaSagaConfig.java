package com.ex.route_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaSagaConfig {

    @Bean
    public NewTopic sagaMainTopic() {
        return new NewTopic("saga-main-topic", 3, (short) 1)
                .configs(Map.of(
                        "retention.ms", "3600000",
                        "cleanup.policy", "delete",
                        "max.message.bytes", "1048576"
                ));
    }

    @Bean
    public KafkaTemplate<String, String> kafkaSagaMainTemplate() {
        return new KafkaTemplate<>(producerSagaMainFactory());
    }

    @Bean
    public ProducerFactory<String, String> producerSagaMainFactory() {
        DefaultKafkaProducerFactory<String, String> factory =
                new DefaultKafkaProducerFactory<>(producerSagaMainConfigs());
        return factory;
    }

    private Map<String, Object> producerSagaMainConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }


    // consumer
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaSagaMainListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerSagaMainFactory());
        return factory;
    }

    private ConsumerFactory<String, String> consumerSagaMainFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "tr-group");
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
