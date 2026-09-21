package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.model.Inventory;
import com.intellect.financeTracker.repository.InventoryRepository;
import com.intellect.financeTracker.repository.UserRepository;
import com.intellect.financeTracker.repository.VendorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Inventory addInventory(Long userId, Inventory inventory) {
        validateUserExists(userId);
        inventory.setUserId(userId);

        // Validate vendor exists for this user if provided
        if (inventory.getVendorId() != null) {
            com.intellect.financeTracker.model.Vendor vendor = vendorRepository
                    .findByVendorIdAndUserId(inventory.getVendorId(), userId)
                    .orElseThrow(() -> new RuntimeException("Vendor not found for this user"));
            inventory.setVendorName(vendor.getName());
        }

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(Long userId, Long inventoryId, Inventory inventory) {
        validateUserExists(userId);

        Inventory existing = inventoryRepository.findByInventoryIdAndUserId(inventoryId, userId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for this user"));

        existing.setItemName(inventory.getItemName());
        existing.setCategory(inventory.getCategory());
        existing.setUnit(inventory.getUnit());
        existing.setUnitCost(inventory.getUnitCost());
        existing.setQuantity(inventory.getQuantity());
        existing.setUsedQuantity(inventory.getUsedQuantity());
        existing.setMinimumQuantity(inventory.getMinimumQuantity());
        existing.setNote(inventory.getNote());
        existing.setCurrentDate(inventory.getCurrentDate());

        // Validate vendor if it is being linked
        if (inventory.getVendorId() != null) {
            com.intellect.financeTracker.model.Vendor vendor = vendorRepository
                    .findByVendorIdAndUserId(inventory.getVendorId(), userId)
                    .orElseThrow(() -> new RuntimeException("Vendor not found for this user"));
            existing.setVendorId(vendor.getVendorId());
            existing.setVendorName(vendor.getName());
        } else {
            existing.setVendorId(null);
            existing.setVendorName(null);
        }

        return inventoryRepository.save(existing);
    }

    @Override
    public void deleteInventory(Long userId, Long inventoryId) {
        validateUserExists(userId);

        Inventory inventory = inventoryRepository.findByInventoryIdAndUserId(inventoryId, userId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for this user"));

        inventoryRepository.delete(inventory);
    }

    @Override
    public Inventory getInventoryById(Long userId, Long inventoryId) {
        validateUserExists(userId);

        return inventoryRepository.findByInventoryIdAndUserId(inventoryId, userId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for this user"));
    }

    @Override
    public List<Inventory> getAllInventory(Long userId) {
        validateUserExists(userId);
        return inventoryRepository.findByUserId(userId);
    }

    @Override
    public List<Inventory> getInventoryByVendor(Long userId, Long vendorId) {
        validateUserExists(userId);

        if (!vendorRepository.findByVendorIdAndUserId(vendorId, userId).isPresent()) {
            throw new RuntimeException("Vendor not found for this user");
        }

        return inventoryRepository.findByVendorIdAndUserId(vendorId, userId);
    }
}