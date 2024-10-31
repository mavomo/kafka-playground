package com.playground.kafkaplayground;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.playground.kafkaplayground.domain.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerIT extends TestConfiguration {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldFetchProducts() throws Exception {
        var productAsJson = mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andReturn();

        String json = productAsJson.getResponse().getContentAsString();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class);
        List<Product> products = objectMapper.readValue(json, collectionType);

        assertNotNull(products);
        assertEquals(100, products.size());
    }
}
