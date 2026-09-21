package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.Bill;

import java.util.List;

public interface BillServiceInteface {

    Bill insertRecord(Bill bill, Long userId, Long voucherId);

    Bill updateRecord(Long id, Bill bill);

    void deleteRecord(Long id);

    List<Bill> getAll();

    Bill getById(Long id);

    List<Bill> getByUserId(Long userId);
}
