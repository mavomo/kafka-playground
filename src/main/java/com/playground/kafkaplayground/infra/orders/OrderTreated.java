package com.playground.kafkaplayground.infra.orders;

import com.playground.kafkaplayground.domain.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public record OrderTreated(String id, List<OrderItem> items, LocalDateTime treatedAt) {
}
