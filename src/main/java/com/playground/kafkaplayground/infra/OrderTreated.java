package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public record OrderTreated(String Id, List<OrderItem> item, LocalDateTime treatedAt) {
}
