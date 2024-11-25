package com.playground.kafkaplayground.infra.config.kafka.producer;

import com.playground.kafkaplayground.infra.config.kafka.KafkaProperties;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaProducerConfiguration<K, V> {
    private final KafkaProperties kafkaProperties;

    public KafkaProducerConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        System.out.println(" inside the producerConfigs() method");

        // Connection
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        // SASL Authentication
        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
        configProps.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSaslMechanism());
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                "org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s';",
                kafkaProperties.getJaasUsername(),
                kafkaProperties.getJaasPassword()
        ));

        // SerDes
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        // Specific producer configurations
        configProps.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());
        configProps.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getProducer().getRetries());
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getProducer().getBatchSize());
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getProducer().getLingerMs());
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getProducer().getBufferMemory());
        return configProps;
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        ProducerFactory<K, V> kvProducerFactory = producerFactory();
        KafkaTemplate<K, V> kafkaTemplate = new KafkaTemplate<>(kvProducerFactory);
        kafkaTemplate.setProducerListener(new LoggingProducerListener<>());
        return kafkaTemplate;
    }

}
