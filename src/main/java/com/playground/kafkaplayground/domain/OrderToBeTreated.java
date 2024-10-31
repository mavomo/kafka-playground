package com.playground.kafkaplayground.domain;

import java.util.List;

public record OrderToBeTreated(List<OrderItem> items) {

    public record OrderItem(Product product, int quantity) {
    }
}
