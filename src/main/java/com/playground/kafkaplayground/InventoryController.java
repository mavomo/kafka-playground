package com.playground.kafkaplayground;

import com.playground.kafkaplayground.infra.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("hello")
    public ResponseEntity<String> helloInventory() {
        inventoryService.sendMessage("dev.playground.inventory.created", UUID.randomUUID().toString(), "hello inventory");
        return ResponseEntity.ok().build();
    }
}
