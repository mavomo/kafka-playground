package com.playground.kafkaplayground.domain;

import java.time.LocalDateTime;

public record Inventory(
        LocalDateTime createdAt,
        Long productId,
        Long productQuantity
) {
}
