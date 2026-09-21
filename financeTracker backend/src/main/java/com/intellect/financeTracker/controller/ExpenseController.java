package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Expense;
import com.intellect.financeTracker.service.ExpenseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER')")
public class ExpenseController {
    @Autowired
    private ExpenseServiceInterface expenseService;

    // POST: /expenses/1
    @PostMapping("/{userId}")
    public ResponseEntity<Expense> create(@PathVariable("userId") Long userId, @RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.insertRecord(expense, userId));
    }

    // GET: /expenses/user/1w
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(expenseService.getExpensesByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(expenseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(@PathVariable("id") Long id, @RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.updateRecord(id, expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        expenseService.deleteRecord(id);
        return ResponseEntity.ok("Expense deleted successfully.");
    }
}
