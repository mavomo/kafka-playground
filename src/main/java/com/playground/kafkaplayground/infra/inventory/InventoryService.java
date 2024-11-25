package com.playground.kafkaplayground.infra.inventory;

import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.infra.products.ProductService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    public static final String INVENTORY_CREATED_TOPIC = "dev.playground.inventory.created";
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
                    INVENTORY_CREATED_TOPIC,
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
