package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.Expense;

import java.util.List;

public interface ExpenseServiceInterface {

    Expense insertRecord(Expense expense, Long userId);

    Expense updateRecord(Long id, Expense expense);

    void deleteRecord(Long id);

    List<Expense> getAll();

    Expense getById(Long id);

    List<Expense> getExpensesByUserId(Long userId);
}
