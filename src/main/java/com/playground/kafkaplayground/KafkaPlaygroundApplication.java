package com.playground.kafkaplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class KafkaPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaPlaygroundApplication.class, args);
    }

}
