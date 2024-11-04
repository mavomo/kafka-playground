package com.playground.kafkaplayground.infra.config.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaStreamFactory {

    private final KafkaProperties kafkaProperties;

    public KafkaStreamFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> properties = new HashMap<>();
        // Connection
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        // SASL Authentication
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
        properties.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSaslMechanism());
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                "org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s';",
                kafkaProperties.getJaasUsername(),
                kafkaProperties.getJaasPassword()
        ));
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "inventory-management");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
        properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        properties.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");

        return new KafkaStreamsConfiguration(properties);
    }
}
