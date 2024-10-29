package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Inventory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InventoryService {

    private final KafkaTemplate<String, Inventory> kafkaTemplate;

    public InventoryService(KafkaTemplate<String, Inventory> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, new Inventory(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                1L,
                1L
        ));
    }
}
