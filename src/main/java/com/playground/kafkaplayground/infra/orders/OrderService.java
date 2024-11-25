package com.playground.kafkaplayground.infra.orders;

import com.playground.kafkaplayground.domain.OrderToBeTreated;
import com.playground.kafkaplayground.infra.products.ProductService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    public static final String ORDER_TREATED_TOPIC = "dev.playground.order.treated";

    private final ProductService productService;
    private final Map<String, OrderTreated> orders = new HashMap<>();
    private final KafkaTemplate<String, OrderTreated> kafkaTemplate;

    public OrderService(ProductService productService, KafkaTemplate<String, OrderTreated> kafkaTemplate) {
        this.productService = productService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<OrderTreated> getOrders() {
        return orders.values().stream().toList();
    }

    public String createOrder(OrderToBeTreated order) {
        if (order.items().isEmpty()) {
            throw new OrderException("Order is empty of products!");
        }
        order.items().forEach(item -> {
            if (productService.getProductById(item.productId()) == null) {
                throw new OrderException("Product not found for productId" + item.productId());
            }
        });

        String orderId = "ref-kfk-" + UUID.randomUUID().toString().substring(0, 5);
        OrderTreated orderTreated = new OrderTreated(orderId, order.items(), LocalDateTime.now());
        orders.put(orderId, orderTreated);

        var orderRecord = new ProducerRecord<>(
                ORDER_TREATED_TOPIC,
                orderId,
                orderTreated
        );
        this.kafkaTemplate.send(orderRecord);
        return orderId;
    }
}

