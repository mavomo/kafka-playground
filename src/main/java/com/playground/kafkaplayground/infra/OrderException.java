package com.playground.kafkaplayground.infra;

public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
