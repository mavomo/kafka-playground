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
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
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

        StoreBuilder<KeyValueStore<String, Inventory>> inventoryStore = Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore(INVENTORY_STORE),
                Serdes.String(),
                new JsonSerde<>(Inventory.class)
        );
        streamsBuilder.addStateStore(inventoryStore);

        KStream<String, OrderTreated> orderStream = streamsBuilder.stream(ORDER_TREATED_TOPIC, orderTreatedConsumer)
                .peek((key, order) -> log.info("Processing order: {}", order.id()));

        KTable<String, Inventory> inventoryTable = streamsBuilder
                .table(INVENTORY_CREATED_TOPIC,  Materialized.<String, Inventory, KeyValueStore<Bytes, byte[]>>as("inventory-ktable")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(new JsonSerde<>(Inventory.class)));


        orderStream.flatMapValues(OrderTreated::items)
                .transform(() -> new Transformer<String, OrderItem, KeyValue<String, Inventory>>() {

                    private ProcessorContext context;
                    String storeName = inventoryTable.queryableStoreName();

                    ReadOnlyKeyValueStore<String, Inventory> store = context.getStateStore(storeName);

                    @Override
                    public void init(ProcessorContext context) {
                        this.context = context;
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
                        long currentQuantity = currentInventory != null ?
                                currentInventory.productQuantity() :
                                DEFAULT_PRODUCT_QUANTITY;

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
