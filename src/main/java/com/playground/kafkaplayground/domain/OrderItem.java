package com.playground.kafkaplayground.domain;

public record OrderItem(Product product, int quantity) {
    public Long getProductId() {
        return product.id();
    }
}
