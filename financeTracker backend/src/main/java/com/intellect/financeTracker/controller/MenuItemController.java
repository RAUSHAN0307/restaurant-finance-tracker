package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.MenuItem;
import com.intellect.financeTracker.service.implementation.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
// @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
public class MenuItemController {

    @Autowired
    private MenuItemService service;

    @PostMapping("/{userId}")
    public ResponseEntity<MenuItem> create(@PathVariable("userId") Long userId, @RequestBody MenuItem item) {
        return new ResponseEntity<>(service.insertRecord(userId, item), HttpStatus.CREATED);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<MenuItem>> getRestaurantMenu(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(service.getAllByRestaurant(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> update(@PathVariable("id") Long id, @RequestBody MenuItem item) {
        return ResponseEntity.ok(service.updateRecord(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.deleteRecord(id);
        return ResponseEntity.ok("Item deleted from menu.");
    }
}
