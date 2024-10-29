package com.playground.kafkaplayground;

import com.playground.kafkaplayground.domain.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final Map<String, Order> orders = new HashMap<>();

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        var orderId = String.join("", "api/orders/", order.id().toString());
        orders.put(orderId, order);
        return ResponseEntity.created(URI.create(orderId)).build();
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orders.values().stream().toList());
    }
}
