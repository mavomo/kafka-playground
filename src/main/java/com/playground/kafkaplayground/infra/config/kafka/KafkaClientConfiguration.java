package com.playground.kafkaplayground.infra.config.kafka;

import com.playground.kafkaplayground.domain.Inventory;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Profile("kafka")
@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaClientConfiguration<K, V> {

    private final KafkaProperties kafkaProperties;

    public KafkaClientConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, Inventory> inventoryConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        // Connection
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        // SASL Authentication
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
        properties.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSaslMechanism());
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                "org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s';",
                kafkaProperties.getJaasUsername(),
                kafkaProperties.getJaasPassword()
        ));

        // Des
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(
                StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
                ConsumerExceptionHandler.class.getName()
        );

        // Specific
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Inventory> inventoryKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Inventory> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(inventoryConsumerFactory());
        AtomicBoolean resetOffsets = new AtomicBoolean(true); // Control flag

        factory.getContainerProperties().setConsumerRebalanceListener(new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                if (resetOffsets.get() && !partitions.isEmpty()) {
                    Consumer<?, ?> consumer = factory.getConsumerFactory().createConsumer();
                    consumer.seekToBeginning(partitions);
                    resetOffsets.set(false); // Only reset once
                }
            }

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                // Nothing needed here
            }
        });
        return factory;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

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
