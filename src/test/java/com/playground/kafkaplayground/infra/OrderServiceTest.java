package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.OrderToBeTreated;
import com.playground.kafkaplayground.domain.Product;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private MockProducer<String, OrderTreated> mockProducer;

    @Mock
    private KafkaTemplate<String, OrderTreated> kafkaTemplate;

    private OrderService orderService;

    private OrderToBeTreated sampleOrder;

    @BeforeEach
    void setUp() {
        mockProducer = new MockProducer<>(
                true,
                new StringSerializer(),
                new JsonSerializer<>()
        );

        orderService = new OrderService(kafkaTemplate);

        // Initialize test data
        var price = new Product.Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        var product = new Product(1L, "Product 1", price);

        sampleOrder = new OrderToBeTreated(
                List.of(new OrderItem(product, 1)));

    }

    @Test
    void should_register_the_order_into_kafka_topic() {
        when(kafkaTemplate.send(Mockito.any(ProducerRecord.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        String orderId = orderService.createOrder(sampleOrder);

        assertThat(orderId).startsWith("ref-kfk-");
    }
}
