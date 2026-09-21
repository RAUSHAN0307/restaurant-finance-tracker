package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Bill;
import com.intellect.financeTracker.service.BillServiceInteface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'WAITER')")
// @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
public class BillController {
    @Autowired
    private BillServiceInteface billService;

    // INSERT: POST /bills/user/1/voucher/10
    @PostMapping("/user/{userId}/voucher/{voucherId}")
    public ResponseEntity<Bill> create(
            @PathVariable("userId") Long userId,
            @PathVariable("voucherId") Long voucherId,
            @RequestBody Bill bill) {
        return ResponseEntity.ok(billService.insertRecord(bill, userId, voucherId));
    }

    // GET ALL BY USER: GET /api/bills/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bill>> getByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(billService.getByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(billService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> update(@PathVariable("id") Long id, @RequestBody Bill bill) {
        return ResponseEntity.ok(billService.updateRecord(id, bill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        billService.deleteRecord(id);
        return ResponseEntity.ok("Bill deleted successfully.");
    }
}
