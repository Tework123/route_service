package com.ex.route_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTrConfig {

    //    тестировал транзакционность на стороне продусеров.
    //    На стороне консумеров транзакционность не обеспечивается
    @Bean
    public NewTopic trTopic() {
        return new NewTopic("tr-topic", 3, (short) 1)
                .configs(Map.of(
                        "retention.ms", "3600000",
                        "cleanup.policy", "delete",
                        "max.message.bytes", "1048576"
                ));
    }

    @Bean
    public ProducerFactory<String, String> producerTrFactory() {
        DefaultKafkaProducerFactory<String, String> factory =
                new DefaultKafkaProducerFactory<>(producerConfigs());
        factory.setTransactionIdPrefix("my-tx-");  // включаем транзакции
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTrTemplate() {
        return new KafkaTemplate<>(producerTrFactory());
    }

    @Bean
    public KafkaTransactionManager<String, String> transactionManager() {
        return new KafkaTransactionManager<>(producerTrFactory());
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // убирает дубликаты
        // transactional.id генерируется с префиксом my-tx-
        return props;
    }

    @Bean
    public ProducerFactory<String, String> nonTxProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs()); // без setTransactionIdPrefix
    }

    @Bean
    public KafkaTemplate<String, String> nonTxKafkaTemplate() {
        return new KafkaTemplate<>(nonTxProducerFactory());
    }
}
