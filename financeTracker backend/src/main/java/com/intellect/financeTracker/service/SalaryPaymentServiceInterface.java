package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.SalaryPayments;

import java.util.List;

public interface SalaryPaymentServiceInterface {

    SalaryPayments insertRecord(Long empId, SalaryPayments salaryPayment);

    SalaryPayments updateRecord(Long id, SalaryPayments salaryPayment);

    void deleteRecord(Long id);

    List<SalaryPayments> getAllRecords();

    SalaryPayments getRecordById(Long id);

}
