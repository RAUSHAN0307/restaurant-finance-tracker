package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.Voucher;

import java.util.List;

public interface VoucherServiceInterface {

    Voucher insertRecord(Voucher voucher, Long userId);

    Voucher updateRecord(Long id, Voucher voucher);

    void deleteRecord(Long id);

    List<Voucher> getAll();

    Voucher getById(Long id);

    // New Method
    List<Voucher> getVouchersByUserId(Long userId);
}
