package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.ProductService;
import com.playground.kafkaplayground.domain.Inventory;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    private static final String TOPIC_NAME = "dev.playground.inventory.created";
    private static final Long DEFAULT_PRODUCT_QUANTITY = 10L;

    private final KafkaTemplate<String, Inventory> kafkaTemplate;
    private final ProductService productService;

    public InventoryService(KafkaTemplate<String, Inventory> kafkaTemplate, ProductService productService) {
        this.kafkaTemplate = kafkaTemplate;
        this.productService = productService;
    }

    public void initialize() {
        productService.products().forEach(product -> {
            var record = new ProducerRecord<>(
                    TOPIC_NAME,
                    product.id().toString(),
                    new Inventory(
                            LocalDateTime.now(),
                            product.id(),
                            DEFAULT_PRODUCT_QUANTITY
                    ));
            kafkaTemplate.send(record);
        });
    }
}
