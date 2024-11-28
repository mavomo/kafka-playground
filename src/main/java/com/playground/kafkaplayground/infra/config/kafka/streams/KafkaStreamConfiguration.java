package com.playground.kafkaplayground.infra.config.kafka.streams;

import com.playground.kafkaplayground.infra.config.kafka.ConsumerExceptionHandler;
import com.playground.kafkaplayground.infra.config.kafka.KafkaProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaStreamConfiguration {

    private final KafkaProperties kafkaProperties;

    public KafkaStreamConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaStreamsConfiguration defaultKafkaStreamsConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        if (kafkaProperties.isActivateSsl()) {
            properties.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
            properties.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSaslMechanism());
            properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                    "org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s';",
                    kafkaProperties.getJaasUsername(),
                    kafkaProperties.getJaasPassword()
            ));
        }

        // Serde
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
        properties.put(
                StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
                ConsumerExceptionHandler.class.getName()
        );

        // Stream configuration
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "inventory-management");
        properties.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");
        properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        properties.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 3);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(StreamsConfig.producerPrefix(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG), true);

        return new KafkaStreamsConfiguration(properties);
    }

}
