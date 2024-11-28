package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Inventory;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    private final Logger log = LoggerFactory.getLogger(InventoryService.class);

    public static final String INVENTORY_CREATED_TOPIC = "dev.playground.inventory.created";
    private static final Long DEFAULT_PRODUCT_QUANTITY = 10L;

    private final KafkaTemplate<String, Inventory> kafkaTemplate;
    private final ProductService productService;

    public InventoryService(KafkaTemplate<String, Inventory> kafkaTemplate, ProductService productService) {
        this.kafkaTemplate = kafkaTemplate;
        this.productService = productService;
    }

    @PostConstruct
    public void resetOffsets() {

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

    @KafkaListener(
            topics = INVENTORY_CREATED_TOPIC,
            groupId = "inventory-management",
            containerFactory = "inventoryKafkaListenerContainerFactory"
    )
    public void listenInventory(Inventory inventory) {
        log.info("Consume Inventory from groupId[inventory-management]: {}", inventory.toString());
    }

}
