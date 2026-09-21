package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Employee;
import com.intellect.financeTracker.service.implementation.EmployeeService;
import com.intellect.financeTracker.dto.EmployeeRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER')")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // CREATE: Linked to a specific Admin/User
    @PostMapping("/{adminId}")
    public Employee saveEmployee(@PathVariable("adminId") Long adminId,
            @RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        return employeeService.insertRecord(adminId, employeeRegisterDTO);
    }

    // GET ALL for a specific restaurant
    @GetMapping("/user/{userId}")
    public List<Employee> getEmployeeByUserId(@PathVariable("userId") Long userId) {
        return employeeService.getAllByUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getRecordById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") Long id, @RequestBody EmployeeRegisterDTO employee) {
        return ResponseEntity.ok(employeeService.updateRecord(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        employeeService.deleteRecord(id);
        return ResponseEntity.ok("Employee record removed successfully.");
    }
}
