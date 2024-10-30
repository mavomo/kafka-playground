package com.playground.kafkaplayground.infra.config.kafka;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.playground.kafkaplayground.ProductService;
import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.infra.InventoryService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class ClusterConfiguration<K, V> {

    private final KafkaProperties kafkaProperties;

    public ClusterConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        // Basic configuration
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Additional producer configurations
        configProps.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getAcks());
        configProps.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getRetries());
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getBatchSize());
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getLingerMemory());
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getMemoryConfig());

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        ProducerFactory<K, V> kvProducerFactory = producerFactory();
        KafkaTemplate<K, V> kafkaTemplate = new KafkaTemplate<>(kvProducerFactory);
        kafkaTemplate.setProducerListener(new LoggingProducerListener<>());
        return kafkaTemplate;
    }

}
