package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.OrderToBeTreated;
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

    private static final String ORDER_TOPIC_NAME = "dev.playground.order.treated";

    private final Map<String, OrderTreated> orders = new HashMap<>();
    private final KafkaTemplate<String, OrderTreated> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderTreated> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<OrderTreated> getOrders() {
        return orders.values().stream().toList();
    }

    public String createOrder(OrderToBeTreated order) {
        String orderId = "ref-kfk-" + UUID.randomUUID().toString().substring(0, 5);
        OrderTreated orderTreated = new OrderTreated(orderId, order.items(), LocalDateTime.now());
        orders.put(orderId, orderTreated);

        var orderRecord = new ProducerRecord<>(
                ORDER_TOPIC_NAME,
                orderId,
                orderTreated
        );
        this.kafkaTemplate.send(orderRecord);
        return orderId;
    }
}

