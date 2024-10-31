package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.OrderToBeTreated;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    private final Map<String, OrderTreated> orders = new HashMap<>();

    public List<OrderTreated> getOrders() {
        return orders.values().stream().toList();
    }

    public String createOrder(OrderToBeTreated order) {
        String orderId= "ref-kfk-" + UUID.randomUUID();
        OrderTreated orderTreated = new OrderTreated(orderId, order.items(), LocalDateTime.now());
        orders.put(orderId, orderTreated);
        return orderId;
    }
}

