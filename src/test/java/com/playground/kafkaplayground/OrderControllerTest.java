package com.playground.kafkaplayground;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.kafkaplayground.domain.OrderToBeTreated;
import com.playground.kafkaplayground.domain.Product;
import com.playground.kafkaplayground.domain.Product.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestConfigurationIT
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_create_order() throws Exception {
        var price = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        var product = new Product(1L, "Product 1", price);

        OrderToBeTreated orderRequest = new OrderToBeTreated(
                List.of(
                        new OrderToBeTreated.OrderItem(product, 1))
        );

        var requestJson = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("api/orders/ref-kfk-")));
    }

    @Test
    public void should_retrieve_orders() throws Exception {
        var price = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        var product = new Product(1L, "Product 1", price);

        var request = new OrderToBeTreated(
                List.of(new OrderToBeTreated.OrderItem(product, 1)));

        var requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        var orders = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(orders).isEqualTo("""
                [{"id":123,"orderedAt":"2024-10-24T12:02:00","items":[{"product":{"id":1,"name":"Product 1","price":{"amount":100,"currency":"USD"}},"quantity":1}]}]
                """.trim());
    }

}
