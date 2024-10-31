package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.OrderToBeTreated;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    private final Map<String, OrderToBeTreated> orders = new HashMap<>();

    public List<OrderToBeTreated> getOrders() {
        return null;
    }

    public String createOrder(OrderToBeTreated order) {
        String orderId= "ref-kfk-" + UUID.randomUUID();
        orders.put(orderId, order);
        return orderId;
    }
}
