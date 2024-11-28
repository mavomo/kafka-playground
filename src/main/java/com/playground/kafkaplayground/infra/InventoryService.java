package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Inventory;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    public static final String INVENTORY_CREATED_TOPIC = "dev.playground.inventory.created";
    private static final Long DEFAULT_PRODUCT_QUANTITY = 10L;

    private final KafkaTemplate<String, Inventory> kafkaTemplate;
    private final ProductService productService;
    private final InveCon

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

    @KafkaListener(
            topics = INVENTORY_CREATED_TOPIC,
            groupId = "inventory-management",
            containerFactory = "inventoryKafkaListenerContainerFactory"
    )
    public void listenInventoryFromBeginning(Inventory inventory) {
        System.out.println("Received Inventory in group service: " + inventory.toString());
    }

    public void resetConsumer
}
