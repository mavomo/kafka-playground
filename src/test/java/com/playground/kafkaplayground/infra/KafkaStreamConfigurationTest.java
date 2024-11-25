package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.infra.config.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = "random-topic-in")
@EnableConfigurationProperties(KafkaProperties.class)
public @interface KafkaStreamConfigurationTest {
}
