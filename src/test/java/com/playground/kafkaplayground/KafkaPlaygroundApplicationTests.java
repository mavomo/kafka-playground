package com.playground.kafkaplayground;

import com.playground.kafkaplayground.infra.config.kafka.KafkaProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties(KafkaProperties.class)
class KafkaPlaygroundApplicationTests {

    @Test
    void contextLoads() {
    }

}
