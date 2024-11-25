package com.playground.kafkaplayground;

import com.playground.kafkaplayground.infra.KafkaStreamConfigurationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@KafkaStreamConfigurationTest
public class KafkaPlaygroundApplicationTests {

    @Test
    void contextLoads() {
    }

}
