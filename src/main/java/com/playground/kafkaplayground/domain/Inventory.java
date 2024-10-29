package com.playground.kafkaplayground.domain;

import java.time.LocalDateTime;

public record Inventory(
        String Id,
        LocalDateTime createdAt,
        Long productId,
        Long productQuantity
) {
}
