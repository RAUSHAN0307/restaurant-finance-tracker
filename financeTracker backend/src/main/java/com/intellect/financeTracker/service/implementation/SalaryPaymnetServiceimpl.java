package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.EmployeeRepository;
import com.intellect.financeTracker.repository.SalaryPaymentRepository;
import com.intellect.financeTracker.model.Employee;
import com.intellect.financeTracker.model.SalaryPayments;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryPaymnetServiceimpl implements SalaryPaymentServiceInterface {

    @Autowired
    SalaryPaymentRepository salaryRepo;

    @Autowired
    EmployeeRepository empRepo;

    @Override
    public SalaryPayments insertRecord(Long empId, SalaryPayments salaryPayment) {
        // 1. Validate ID and Data
        if (empId == null)
            throw new IllegalArgumentException("Employee ID is missing from URL");
        if (salaryPayment.getAmount() == null)
            throw new IllegalArgumentException("Salary amount is required");
        if (salaryPayment.getPaymentDate() == null)
            throw new IllegalArgumentException("Payment date is required to check the month");

        // 2. Extract Month and Year from the provided payment date
        int month = salaryPayment.getPaymentDate().getMonthValue();
        int year = salaryPayment.getPaymentDate().getYear();

        // 3. Check if salary is already given for this month
        boolean alreadyPaid = salaryRepo.existsByEmployeeAndMonth(empId, month, year);
        if (alreadyPaid) {
            throw new RuntimeException("Salary already given for " + month + "/" + year);
        }

        // 2. Find the Employee (The 'Head Chef' in your image)
        Employee employee = empRepo.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        // 3. Link them and save
        salaryPayment.setEmployee(employee);
        return salaryRepo.save(salaryPayment);
    }

    @Override
    public SalaryPayments updateRecord(Long id, SalaryPayments salaryPayment) {
        return null;
    }

    @Override
    public void deleteRecord(Long id) {

    }

    @Override
    public List<SalaryPayments> getAllRecords() {
        return salaryRepo.findAll();
    }

    @Override
    public SalaryPayments getRecordById(Long id) {
        return salaryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salary record not found."));
    }
}
