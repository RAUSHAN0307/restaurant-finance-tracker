package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.MenuItemRepository;
import com.intellect.financeTracker.repository.UserRepository;
import com.intellect.financeTracker.model.MenuItem;
import com.intellect.financeTracker.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService implements MenuItemServiceInterface {

    @Autowired
    private MenuItemRepository menuRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public MenuItem insertRecord(Long userId, MenuItem item) {
        // Validations
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        if (item.getPrice() == null || item.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be a positive value.");
        }

        User restaurant = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant User not found with ID: " + userId));

        item.setUser(restaurant);
        return menuRepo.save(item);
    }

    @Override
    public MenuItem updateRecord(Long itemId, MenuItem newData) {
        MenuItem existing = menuRepo.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found."));

        if (newData.getItemName() != null)
            existing.setItemName(newData.getItemName());
        if (newData.getPrice() != null)
            existing.setPrice(newData.getPrice());
        if (newData.getCategory() != null)
            existing.setCategory(newData.getCategory());
        if (newData.getStatus() != null)
            existing.setStatus(newData.getStatus());

        return menuRepo.save(existing);
    }

    @Override
    public void deleteRecord(Long itemId) {
        if (!menuRepo.existsById(itemId))
            throw new EntityNotFoundException("Item not found.");
        menuRepo.deleteById(itemId);
    }

    @Override
    public List<MenuItem> getAllByRestaurant(Long userId) {
        return menuRepo.findByUser_UserId(userId);
    }

    @Override
    public MenuItem getById(Long itemId) {
        return menuRepo.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found."));
    }
}
