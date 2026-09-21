package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Inventory;
import com.intellect.financeTracker.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/inventory")
@PreAuthorize("hasAnyRole('OWNER')")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/{userId}/add")
    public Inventory addInventory(
            @PathVariable("userId") Long userId,
            @RequestBody Inventory inventory) {

        return inventoryService.addInventory(userId, inventory);
    }

    @PutMapping("/{userId}/update/{inventoryId}")
    public Inventory updateInventory(
            @PathVariable("userId") Long userId,
            @PathVariable("inventoryId") Long inventoryId,
            @RequestBody Inventory inventory) {

        return inventoryService.updateInventory(userId, inventoryId, inventory);
    }

    @DeleteMapping("/{userId}/delete/{inventoryId}")
    public ResponseEntity<Map<String, Object>> deleteInventory(
            @PathVariable("userId") Long userId,
            @PathVariable("inventoryId") Long inventoryId) {
        inventoryService.deleteInventory(userId, inventoryId);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Inventory deleted successfully"));
    }

    @GetMapping("/{userId}/get/{inventoryId}")
    public Inventory getInventoryById(
            @PathVariable("userId") Long userId,
            @PathVariable("inventoryId") Long inventoryId) {

        return inventoryService.getInventoryById(userId, inventoryId);
    }

    @GetMapping("/{userId}/all")
    public List<Inventory> getAllInventory(
            @PathVariable("userId") Long userId) {

        return inventoryService.getAllInventory(userId);
    }

    @GetMapping("/{userId}/vendor/{vendorId}")
    public List<Inventory> getInventoryByVendor(
            @PathVariable("userId") Long userId,
            @PathVariable("vendorId") Long vendorId) {

        return inventoryService.getInventoryByVendor(userId, vendorId);
    }
}
