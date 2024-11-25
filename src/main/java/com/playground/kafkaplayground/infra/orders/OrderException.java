package com.playground.kafkaplayground.infra.orders;

public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
