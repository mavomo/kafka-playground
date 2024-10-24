package com.playground.kafkaplayground.domain;

import java.math.BigDecimal;
import java.util.Currency;

public record Product(
        Long id,
        String name,
        Price price) {
    public record Price(BigDecimal amount, Currency currency) {
    }
}

