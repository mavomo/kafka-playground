package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.Product;
import com.playground.kafkaplayground.infra.config.kafka.KafkaStreamConfiguration;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.playground.kafkaplayground.infra.InventoryService.INVENTORY_CREATED_TOPIC;
import static com.playground.kafkaplayground.infra.OrderService.ORDER_TREATED_TOPIC;
import static com.playground.kafkaplayground.infra.ProductService.DEFAULT_PRODUCT_QUANTITY;
import static org.apache.kafka.streams.kstream.Materialized.as;

@Profile("kafka")
@Service
public class InventoryPipeline {

    private final Logger log = LoggerFactory.getLogger(InventoryPipeline.class);
    private final ProductService productService;

    private final KafkaStreamConfiguration kafkaStreamConfig;

    public InventoryPipeline(ProductService productService, KafkaStreamConfiguration kafkaStreamConfig) {
        this.productService = productService;
        this.kafkaStreamConfig = kafkaStreamConfig;
    }

    @Bean
    public KStream<String, OrderTreated> buildInventoryStream(StreamsBuilder streamsBuilder) {

        Consumed<String, OrderTreated> orderTreatedConsumer = Consumed.with(Serdes.String(), new JsonSerde<>(OrderTreated.class));
        Produced<String, Inventory> inventoryProducer = Produced.with(Serdes.String(), new JsonSerde<>(Inventory.class));

        // Create KTable from existing inventory topic
        streamsBuilder.table(
                INVENTORY_CREATED_TOPIC,
                Materialized.<String, Inventory, KeyValueStore<Bytes, byte[]>>as("inventory-store")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(new JsonSerde<>(Inventory.class))
        );

        //Declare kafkaStreams instance
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), kafkaStreamConfig.kafkaStreamsConfiguration().asProperties());

        KStream<String, OrderTreated> orderStream = streamsBuilder.stream(ORDER_TREATED_TOPIC, orderTreatedConsumer)
                .peek((key, order) -> log.info("Processing order: {}", order.id()));

        orderStream.flatMapValues(order -> order.items()
                        .stream()
                        .map(item -> createInventory(item, kafkaStreams))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
                .selectKey((key, update) -> update.productId().toString())
                .to(INVENTORY_CREATED_TOPIC, inventoryProducer);

        return orderStream;
    }

    private Inventory createInventory(OrderItem item, KafkaStreams kafkaStreams) {
        Product product = productService.getProductById(item.productId());
        if (product == null) {
            log.warn("Product {} not found", item.productId());
            return null;
        }

        String productKey = item.productId().toString();

        // Get the current inventory from materialized view
        Inventory currentInventory = getCurrentInventory(productKey, kafkaStreams);
        long currentQuantity = currentInventory != null ? currentInventory.productQuantity() : DEFAULT_PRODUCT_QUANTITY;
        // Calculate new quantity
        long newQuantity = currentQuantity - item.quantity();
        if (newQuantity < 0) {
            log.warn("Negative inventory for product: {}", item.productId());
        }

        var newInventory = new Inventory(LocalDateTime.now(), item.productId(), newQuantity);
        log.info("New inventory: {}", newInventory);
        return newInventory;
    }

    public Inventory getCurrentInventory(String productId, KafkaStreams kafkaStreams) {
        ReadOnlyKeyValueStore<String, Inventory> store =
                kafkaStreams.store(StoreQueryParameters.fromNameAndType(
                        "inventory-store",
                        QueryableStoreTypes.keyValueStore()
                ));
        return store.get(productId);

    }

}
