package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.BillItem;
import com.intellect.financeTracker.service.BillItemServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill-items")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'WAITER')")
// @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
public class BillItemController {
    @Autowired
    private BillItemServiceInterface billItemService;

    // URL Mapping: POST /bill-items/bill/5/item/12
    @PostMapping("/bill/{billId}/item/{itemId}")
    public ResponseEntity<BillItem> create(
            @PathVariable("billId") Long billId,
            @PathVariable("itemId") Long itemId,
            @RequestBody BillItem billItem) {
        return ResponseEntity.ok(billItemService.insertRecord(billItem, billId, itemId));
    }

    // Get all items belonging to a specific bill
    @GetMapping("/bill/{billId}")
    public ResponseEntity<List<BillItem>> getByBill(@PathVariable("billId") Long billId) {
        return ResponseEntity.ok(billItemService.getByBillId(billId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillItem> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(billItemService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillItem> update(@PathVariable("id") Long id, @RequestBody BillItem billItem) {
        return ResponseEntity.ok(billItemService.updateRecord(id, billItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        billItemService.deleteRecord(id);
        return ResponseEntity.ok("Item removed from bill.");
    }
}
