package com.playground.kafkaplayground;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.kafkaplayground.domain.OrderItem;
import com.playground.kafkaplayground.domain.OrderToBeTreated;
import com.playground.kafkaplayground.domain.Product;
import com.playground.kafkaplayground.domain.Product.Price;
import com.playground.kafkaplayground.infra.OrderService;
import com.playground.kafkaplayground.infra.OrderTreated;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestConfigurationIT
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderServiceMock;

    @Test
    public void should_create_order_to_be_created_and_return_its_ID_in_location() throws Exception {
        var price = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        var product = new Product(1L, "Product 1", price);

        OrderToBeTreated orderRequest = new OrderToBeTreated(
                List.of(
                        new OrderItem(product, 1))
        );

        Mockito.when(orderServiceMock.createOrder(orderRequest)).thenReturn("ref-kfk-123");

        var requestJson = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("api/orders/ref-kfk-123")));
    }

    @Test
    public void should_retrieve_treated_orders() throws Exception {
        var price = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        var product = new Product(1L, "Product 1", price);

        var ordersToBeTreated = new OrderToBeTreated(
                List.of(new OrderItem(product, 1)));

        var requestJson = objectMapper.writeValueAsString(ordersToBeTreated);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        OrderTreated orderTreated = new OrderTreated(
                "ref-kfk-123",
                ordersToBeTreated.items(),
                LocalDateTime.of(2024, 10, 31, 12, 0, 0)
        );

        Mockito.when(orderServiceMock.getOrders()).thenReturn(List.of(orderTreated));

        var orders = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(orders).isEqualTo("""
                [{"Id":"ref-kfk-123","item":[{"product":{"id":1,"name":"Product 1","price":{"amount":100,"currency":"USD"}},"quantity":1}],"treatedAt":"2024-10-31T12:00:00"}]
                """.trim());
    }

}
