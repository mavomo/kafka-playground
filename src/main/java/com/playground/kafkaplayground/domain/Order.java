package com.playground.kafkaplayground.domain;

import java.time.LocalDateTime;
import java.util.List;

public record Order(Long id, LocalDateTime orderedAt, List<OrderItem> items) {

    public record OrderItem(Product product, int quantity) {
    }
}
