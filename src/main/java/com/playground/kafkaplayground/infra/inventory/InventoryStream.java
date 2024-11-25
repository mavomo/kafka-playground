package com.playground.kafkaplayground.infra.inventory;

import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.Product;
import com.playground.kafkaplayground.infra.orders.OrderToInventoryTransformer;
import com.playground.kafkaplayground.infra.orders.OrderTreated;
import com.playground.kafkaplayground.infra.products.ProductService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.playground.kafkaplayground.infra.inventory.InventoryService.INVENTORY_CREATED_TOPIC;
import static com.playground.kafkaplayground.infra.orders.OrderService.ORDER_TREATED_TOPIC;
import static com.playground.kafkaplayground.infra.products.ProductService.DEFAULT_PRODUCT_QUANTITY;

@Service
public class InventoryStream {

    public static final String INVENTORY_STORE = "inventory-ktable";
    public static final String INVENTORY_TOPIC = "dev.playground.inventory.created";

    private final Logger log = LoggerFactory.getLogger(InventoryStream.class);

    private final ProductService productService;

    public InventoryStream(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public KStream<String, OrderTreated> buildInventoryStream(StreamsBuilder streamsBuilder) {

        streamsBuilder
                .table(INVENTORY_CREATED_TOPIC,
                        Materialized.<String, Inventory, KeyValueStore<Bytes, byte[]>>as(INVENTORY_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(new JsonSerde<>(Inventory.class)));

        Consumed<String, OrderTreated> orderTreatedConsumer = Consumed.with(Serdes.String(), new JsonSerde<>(OrderTreated.class));
        Produced<String, Inventory> inventoryProducer = Produced.with(Serdes.String(), new JsonSerde<>(Inventory.class));

        KStream<String, OrderTreated> orderStream = streamsBuilder
                .stream(ORDER_TREATED_TOPIC, orderTreatedConsumer)
                .peek((key, order) -> log.info("Processing order: {}", order.id()));


        orderStream
                .flatMapValues(OrderTreated::items)
                .transform(() -> new OrderToInventoryTransformer(productService), INVENTORY_STORE)
                .selectKey((key, update) -> update.productId().toString())
                .to(INVENTORY_CREATED_TOPIC, inventoryProducer);

        log.info("KStream topology: ${}", streamsBuilder.build().describe().toString());

        return orderStream;
    }
}
