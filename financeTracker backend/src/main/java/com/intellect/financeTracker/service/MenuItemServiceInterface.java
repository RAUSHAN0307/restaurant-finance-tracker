package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.MenuItem;

import java.util.List;

public interface MenuItemServiceInterface {

    MenuItem insertRecord(Long userId, MenuItem item);

    MenuItem updateRecord(Long itemId, MenuItem item);

    void deleteRecord(Long itemId);

    List<MenuItem> getAllByRestaurant(Long userId);

    MenuItem getById(Long itemId);
}
