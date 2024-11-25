package com.playground.kafkaplayground.infra.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeInventory() {
        inventoryService.initialize();
        return ResponseEntity.ok().build();
    }
}
