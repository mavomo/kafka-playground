package com.playground.kafkaplayground.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.Product;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.playground.kafkaplayground.infra.InventoryService.INVENTORY_CREATED_TOPIC;
import static com.playground.kafkaplayground.infra.OrderService.ORDER_TREATED_TOPIC;
import static com.playground.kafkaplayground.infra.ProductService.DEFAULT_PRODUCT_QUANTITY;

@Component
public class InventoryStreamManagement {

    private final Logger log = LoggerFactory.getLogger(InventoryStreamManagement.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductService productService;

    public InventoryStreamManagement(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public KStream<String, OrderTreated> buildInventoryStream(StreamsBuilder streamsBuilder) {

        Consumed<String, OrderTreated> orderTreatedConsumer = Consumed.with(Serdes.String(), new JsonSerde<>(OrderTreated.class, objectMapper));
        Produced<String, Inventory> inventoryProducer = Produced.with(Serdes.String(), new JsonSerde<>(Inventory.class, objectMapper));

        KStream<String, OrderTreated> orderStream = streamsBuilder.stream(ORDER_TREATED_TOPIC, orderTreatedConsumer)
                .peek((key, order) -> log.info("Processing order: {}", order.id()));

        orderStream.flatMapValues(order -> order.items()
                        .stream()
                        .map(this::createInventory)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
                .selectKey((key, update) -> update.productId().toString())
                .to(INVENTORY_CREATED_TOPIC, inventoryProducer);

        return orderStream;
    }

    private Inventory createInventory(OrderItem item) {
        Product product = productService.getProductById(item.product().id());

        if (product == null) {
            log.warn("Product not found: {}", item.product().id());
            return null;
        }

        long newQuantity = DEFAULT_PRODUCT_QUANTITY - item.quantity();
        if (newQuantity < 0) {
            log.warn("Negative inventory for product: {}", item.product().id());
        }

        return new Inventory(LocalDateTime.now(), item.product().id(), newQuantity);
    }
}
