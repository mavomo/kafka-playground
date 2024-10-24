package com.playground.kafkaplayground.orders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_create_order() throws Exception {
        var rawResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/orders"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("api/orders/xxxx")))
                .andReturn();

        System.out.println(rawResponse);
    }

}
