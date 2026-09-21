package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.BillItem;

import java.util.List;

public interface BillItemServiceInterface {

    BillItem insertRecord(BillItem billItem, Long billId, Long itemId);

    BillItem updateRecord(Long id, BillItem billItem);

    void deleteRecord(Long id);

    BillItem getById(Long id);

    List<BillItem> getByBillId(Long billId);
}
