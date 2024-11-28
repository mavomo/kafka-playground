package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.Product;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.playground.kafkaplayground.infra.InventoryService.INVENTORY_CREATED_TOPIC;
import static com.playground.kafkaplayground.infra.OrderService.ORDER_TREATED_TOPIC;
import static com.playground.kafkaplayground.infra.ProductService.DEFAULT_PRODUCT_QUANTITY;

@Profile("kafka")
@Service
public class InventoryPipeline {

    public static final String INVENTORY_STORE = "inventory-store";

    private final Logger log = LoggerFactory.getLogger(InventoryPipeline.class);

    private final ProductService productService;

    public InventoryPipeline(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public KStream<String, OrderTreated> buildInventoryStream(StreamsBuilder streamsBuilder) {

        Consumed<String, OrderTreated> orderTreatedConsumer = Consumed.with(Serdes.String(), new JsonSerde<>(OrderTreated.class));
        Produced<String, Inventory> inventoryProducer = Produced.with(Serdes.String(), new JsonSerde<>(Inventory.class));
        Consumed<String, Inventory> inventoryConsumer = Consumed.with(
                Serdes.String(),
                new JsonSerde<>(Inventory.class),
                null,
                Topology.AutoOffsetReset.EARLIEST
        );

        streamsBuilder.addStateStore(Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore(INVENTORY_STORE),
                Serdes.String(),
                new JsonSerde<>(Inventory.class)
        ));

        KStream<String, OrderTreated> orderStream = streamsBuilder.stream(ORDER_TREATED_TOPIC, orderTreatedConsumer)
                .peek((key, order) -> log.info("Processing order: {}", order.id()));

        // TODO : how to at initialize kstream, load all records from the beginning, current configuration is related to last offset (consumer group)
        KStream<String, Inventory> inventoryStream = streamsBuilder.stream(INVENTORY_CREATED_TOPIC, inventoryConsumer.withOffsetResetPolicy(Topology.AutoOffsetReset.EARLIEST))
                .peek((key, inventory) -> log.info("Processing current inventory: {}-{}", inventory.productId(), inventory.productQuantity()));

        inventoryStream.process(() -> new Processor<String, Inventory, Void, Void>() {
            private KeyValueStore<String, Inventory> store;

            @Override
            public void init(org.apache.kafka.streams.processor.api.ProcessorContext<Void, Void> context) {
                Processor.super.init(context);
                this.store = context.getStateStore(INVENTORY_STORE);
                log.info("inventoryStream processor initialized");
            }

            @Override
            public void process(org.apache.kafka.streams.processor.api.Record<String, Inventory> record) {
                store.put(record.key(), record.value());
                log.info("Loaded inventory for product {}: {}", record.key(), record.value());
            }

            @Override
            public void close() {
                log.info("inventoryStream processor closed");
            }
        }, INVENTORY_STORE);

        orderStream.flatMapValues(OrderTreated::items)
                .transform(() -> new Transformer<String, OrderItem, KeyValue<String, Inventory>>() {
                    private KeyValueStore<String, Inventory> store;

                    @Override
                    public void init(ProcessorContext context) {
                        this.store = context.getStateStore(INVENTORY_STORE);
                    }

                    @Override
                    public KeyValue<String, Inventory> transform(String key, OrderItem item) {
                        Product product = productService.getProductById(item.productId());
                        if (product == null) {
                            log.warn("Product {} not found", item.productId());
                            return null;
                        }

                        String productKey = item.productId().toString();

                        // Get current inventory
                        Inventory currentInventory = store.get(productKey); // TODO why is null? issue about Long/String? we'll see!!
                        if (currentInventory == null) {
                            throw new RuntimeException("currentInventory for product id: " + productKey + " not found from the store");
                        }
                        long currentQuantity = currentInventory.productQuantity();

                        // Calculate new quantity
                        long newQuantity = currentQuantity - item.quantity();
                        if (newQuantity < 0) {
                            log.warn("Negative inventory for product: {}", item.productId());
                        }

                        // Create and store new inventory
                        Inventory newInventory = new Inventory(
                                LocalDateTime.now(),
                                item.productId(),
                                newQuantity
                        );
                        store.put(productKey, newInventory);

                        log.info("New inventory: {}", newInventory);
                        return KeyValue.pair(productKey, newInventory);
                    }

                    @Override
                    public void close() {
                    }
                }, INVENTORY_STORE)
                .selectKey((key, update) -> update.productId().toString())
                .to(INVENTORY_CREATED_TOPIC, inventoryProducer);

        log.info("KStream topology: ${}", streamsBuilder.build().describe().toString());

        return orderStream;
    }

}
