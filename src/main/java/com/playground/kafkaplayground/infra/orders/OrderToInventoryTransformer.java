package com.playground.kafkaplayground.infra.orders;

import com.playground.kafkaplayground.domain.Inventory;
import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.Product;
import com.playground.kafkaplayground.infra.products.ProductService;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static com.playground.kafkaplayground.infra.inventory.InventoryStream.INVENTORY_STORE;
import static com.playground.kafkaplayground.infra.products.ProductService.DEFAULT_PRODUCT_QUANTITY;

public class OrderToInventoryTransformer implements Transformer<String, OrderItem, KeyValue<String, Inventory>> {
    private final Logger log = LoggerFactory.getLogger(OrderToInventoryTransformer.class);

    private ProcessorContext context;
    private final ProductService productService;
    private KeyValueStore<String, Inventory> inventoryStore;

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

        // Get current inventory
        Inventory currentInventory = inventoryStore.get(productKey); // TODO why is null? issue about Long/String? we'll see!!
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
}
