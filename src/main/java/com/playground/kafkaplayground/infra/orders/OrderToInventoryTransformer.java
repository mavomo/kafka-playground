package com.playground.kafkaplayground.infra.orders;

import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.Product;
import com.playground.kafkaplayground.infra.products.ProductService;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.playground.kafkaplayground.infra.inventory.InventoryStream.INVENTORY_STORE;
import static com.playground.kafkaplayground.infra.products.ProductService.DEFAULT_PRODUCT_QUANTITY;

public class OrderToInventoryTransformer implements Transformer<String, OrderItem, KeyValue<String, Inventory>> {
    private final Logger log = LoggerFactory.getLogger(OrderToInventoryTransformer.class);

    private ProcessorContext context;
    private final ProductService productService;
    private KeyValueStore<String, ValueAndTimestamp<Inventory>> inventoryStore;

    public OrderToInventoryTransformer(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        inventoryStore = context.getStateStore(INVENTORY_STORE);

    }

    @Override
    public KeyValue<String, Inventory> transform(String key, OrderItem item) {
        Product product = productService.getProductById(item.productId());
        if (product == null) {
            log.warn("Product {} not found", item.productId());
            return null;
        }

        String productKey = item.productId().toString();
        ValueAndTimestamp<Inventory> wrappedInventory = inventoryStore.get(productKey);
        if (wrappedInventory == null) {
            log.warn("::: Nothing returned for wrappedInventory with key {} ", productKey);
            return null;
        } else {
            Inventory currentInventory = wrappedInventory.value(); // TODO why is null? issue about Long/String? we'll see!!
            log.info("::: Inventory to update: {}", currentInventory);

            long currentQuantity = currentInventory != null ?
                    currentInventory.productQuantity() :
                    DEFAULT_PRODUCT_QUANTITY;

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

            ValueAndTimestamp newInventoryWrapper = ValueAndTimestamp.make(newInventory, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            inventoryStore.put(productKey, newInventoryWrapper);
            log.info("::: New inventory wrapped >> : {}", newInventoryWrapper);
            log.info("::: New inventory only: {}", newInventoryWrapper.value());

            return KeyValue.pair(productKey, (Inventory) newInventoryWrapper.value());
        }

    }

    @Override
    public void close() {
    }
}
