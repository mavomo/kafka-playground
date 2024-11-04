package com.playground.kafkaplayground;

import com.playground.kafkaplayground.infra.config.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EnableConfigurationProperties(KafkaProperties.class)
public @interface TestConfigurationIT {
    Class<?>[] value() default {};
}
