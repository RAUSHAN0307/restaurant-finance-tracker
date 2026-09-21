package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.SalaryPayments;
import com.intellect.financeTracker.service.SalaryPaymentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")

public class SalaryPaymentController {

    @Autowired
    SalaryPaymentServiceInterface salaryService;

    @PostMapping("/{id}")
    public ResponseEntity<SalaryPayments> addSalary(@PathVariable("id") Long empId,
            @RequestBody SalaryPayments salaryPayments) {
        SalaryPayments salary = salaryService.insertRecord(empId, salaryPayments);
        return new ResponseEntity<>(salary, HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<SalaryPayments>> viewSalaryDetails() {
        List<SalaryPayments> salaryDetails = salaryService.getAllRecords();
        return new ResponseEntity<>(salaryDetails, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaryPayments> viewSalaryDetailsById(@PathVariable("id") Long id) {
        SalaryPayments salaryDetails = salaryService.getRecordById(id);
        return new ResponseEntity<>(salaryDetails, HttpStatusCode.valueOf(200));
    }

}
