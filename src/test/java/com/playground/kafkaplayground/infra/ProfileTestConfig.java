package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.KafkaPropertiesForTests;
import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.infra.config.kafka.KafkaProperties;
import com.playground.kafkaplayground.infra.inventory.InventoryService;
import com.playground.kafkaplayground.infra.orders.OrderTreated;
import com.playground.kafkaplayground.infra.products.ProductService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;


@TestConfiguration
@EnableConfigurationProperties(KafkaPropertiesForTests.class)
@Profile("test")
public class ProfileTestConfig {

    private final KafkaProperties kafkaProperties;


    public ProfileTestConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    @Bean
    public InventoryService inventoryService(KafkaTemplate<String, Inventory> kafkaTemplate, ProductService productService) {
        return new InventoryService(kafkaTemplate, productService);
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> properties = getKafkaClientProperties();
        return new KafkaStreamsConfiguration(properties);
    }


    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getKafkaClientProperties());
    }

    @Bean
    public StreamsBuilderFactoryBean streamsBuilder() {
        return new StreamsBuilderFactoryBean(kafkaStreamsConfiguration());
    }

    @Bean
    public KStream<String, OrderTreated> buildInventoryStreamStub(StreamsBuilder streamsBuilder) {
        Consumed<String, OrderTreated> orderTreatedConsumer = Consumed.with(Serdes.String(), new JsonSerde<>(OrderTreated.class));

        KStream<String, OrderTreated> orderStream = streamsBuilder.stream("random-topic", orderTreatedConsumer)
                .peek((key, order) -> System.out.println("Processing order: " + order.id()));
        return orderStream;
    }

    private Map<String, Object> getKafkaClientProperties() {
        Map<String, Object> properties = new HashMap<>();

        // Connection
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        // SASL Authentication
        properties.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurityProtocol());
        properties.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSaslMechanism());
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(
                "org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s';",
                kafkaProperties.getJaasUsername(),
                kafkaProperties.getJaasPassword()
        ));

        // Stream configuration
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "inventory-management-test");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
        properties.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");
        properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        properties.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 3);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(StreamsConfig.producerPrefix(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG), true);
        return properties;
    }

}
