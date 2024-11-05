package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.OrderToBeTreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create a new order",
            description = "Creates a new order and returns the location of the created order",
            requestBody = @RequestBody(content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderToBeTreated.class),
                    examples = @ExampleObject(value = """
                            {
                              "items": [
                                {
                                  "product_id": 1,
                                  "quantity": 2
                                }
                              ]
                            }
                            """
                    )
            )),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            })
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderToBeTreated order) {
        String orderId = orderService.createOrder(order);
        var location = String.join("", "api/orders/", orderId);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderTreated>> getAllOrders() {
        List<OrderTreated> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }
}
