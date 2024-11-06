package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.OrderToBeTreated;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderToBeTreated order) {
        String orderId = orderService.createOrder(order);
        var location = String.join("", "api/orders/", orderId);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderTreated>> getAllOrders() {
        List<OrderTreated> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }
}
