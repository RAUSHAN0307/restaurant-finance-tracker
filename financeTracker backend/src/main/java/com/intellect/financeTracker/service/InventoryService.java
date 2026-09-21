package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.Inventory;
import java.util.List;

public interface InventoryService {

    Inventory addInventory(Long userId, Inventory inventory);

    Inventory updateInventory(Long userId, Long inventoryId, Inventory inventory);

    void deleteInventory(Long userId, Long inventoryId);

    Inventory getInventoryById(Long userId, Long inventoryId);

    List<Inventory> getAllInventory(Long userId);

    List<Inventory> getInventoryByVendor(Long userId, Long vendorId);

}