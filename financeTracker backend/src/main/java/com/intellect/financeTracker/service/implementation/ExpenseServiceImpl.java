package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.ExpenceRepository;
import com.intellect.financeTracker.model.Expense;
import com.intellect.financeTracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseServiceInterface {
    @Autowired
    private ExpenceRepository expenseRepository;

    @Override
    public Expense insertRecord(Expense expense, Long userId) {
        if (userId == null) {
            throw new RuntimeException("User ID must be provided in the URL.");
        }

        // Basic Validations
        if (expense.getExpenseName() == null || expense.getExpenseName().trim().isEmpty()) {
            throw new RuntimeException("Expense name is required.");
        }
        if (expense.getAmount() == null || expense.getAmount().doubleValue() <= 0) {
            throw new RuntimeException("Expense amount must be greater than zero.");
        }

        // Auto-set Date if null
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(LocalDate.now());
        }

        // Link User
        User user = new User();
        user.setUserId(userId);
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateRecord(Long id, Expense updatedExpense) {
        Expense existing = getById(id);

        if (updatedExpense.getExpenseName() != null)
            existing.setExpenseName(updatedExpense.getExpenseName());
        if (updatedExpense.getExpenseType() != null)
            existing.setExpenseType(updatedExpense.getExpenseType());
        if (updatedExpense.getAmount() != null)
            existing.setAmount(updatedExpense.getAmount());
        if (updatedExpense.getDescription() != null)
            existing.setDescription(updatedExpense.getDescription());
        if (updatedExpense.getExpenseDate() != null)
            existing.setExpenseDate(updatedExpense.getExpenseDate());

        return expenseRepository.save(existing);
    }

    @org.springframework.transaction.annotation.Transactional
    @Override
    public void deleteRecord(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with ID: " + id));

        // Sever the bidirectional link so the deeply-fetched User doesn't recreate it
        User user = expense.getUser();
        if (user != null && user.getExpenses() != null) {
            user.getExpenses().remove(expense);
        }

        expenseRepository.delete(expense);
    }

    @Override
    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with ID: " + id));
    }

    @Override
    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUser_UserId(userId);
    }
}
