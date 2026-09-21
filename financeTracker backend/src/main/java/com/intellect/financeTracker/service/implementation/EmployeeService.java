package com.intellect.financeTracker.service.implementation;

// import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.EmployeeRepository;
import com.intellect.financeTracker.repository.UserRepository;
import com.intellect.financeTracker.repository.SalaryPaymentRepository;
import com.intellect.financeTracker.model.Employee;
import com.intellect.financeTracker.model.User;
import com.intellect.financeTracker.model.SalaryPayments;
import com.intellect.financeTracker.dto.EmployeeRegisterDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SalaryPaymentRepository salaryPaymentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee insertRecord(Long adminId, EmployeeRegisterDTO employeeDto) {

        // if (employeeDto == null)
        // throw new IllegalArgumentException("Employee data cannot be null.");

        // if (employeeDto.getSalary() == null || employeeDto.getSalary().doubleValue()
        // < 0)
        // throw new IllegalArgumentException("Salary must be valid.");

        // if (employeeDto.getJoinDate() == null)
        // throw new IllegalArgumentException("Join date required.");

        // User admin = userRepository.findById(adminId)
        // .orElseThrow(() -> new EntityNotFoundException("Admin not found"));

        // Employee employee = new Employee();
        // String normalizedRole = employeeDto.getRole() != null ?
        // employeeDto.getRole().toUpperCase() : "EMPLOYEE";
        // System.out.println("role " + normalizedRole);
        // employee.setEmpName(employeeDto.getEmpName());
        // employee.setRole(normalizedRole);
        // employee.setSalary(employeeDto.getSalary());
        // employee.setJoinDate(employeeDto.getJoinDate());
        // employee.setPhone(employeeDto.getPhone());
        // employee.setEmail(employeeDto.getEmail());
        // employee.setUser(admin);
        // Employee savedEmployee = employeeRepository.save(employee);
        // System.out.println("saved employee " + employeeDto.getUserName());
        // System.out.println("saved employee " + employeeDto.getPassword());

        // if (employeeDto.getUserName() != null && employeeDto.getPassword() != null) {
        // System.out.println("inside user creation");
        // User employeeUser = new User();
        // employeeUser.setUserName(employeeDto.getUserName());
        // employeeUser.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        // employeeUser.setEmail(employeeDto.getEmail());
        // System.out.println("role " + employeeDto.getRole());
        // System.out.println("role " + normalizedRole);
        // employeeUser.setRole(normalizedRole);
        // employeeUser.setEmpId(savedEmployee.getEmpId());
        // userRepository.save(employeeUser);
        // }
        // return savedEmployee;

        if (employeeDto == null)
            throw new IllegalArgumentException("Employee data cannot be null.");

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));

        Employee employee = new Employee();
        // Normalize role to uppercase
        String normalizedRole = employeeDto.getRole() != null ? employeeDto.getRole().toUpperCase() : "EMPLOYEE";

        employee.setEmpName(employeeDto.getEmpName());
        employee.setRole(normalizedRole);
        employee.setSalary(employeeDto.getSalary());
        employee.setJoinDate(employeeDto.getJoinDate());
        employee.setPhone(employeeDto.getPhone());
        employee.setEmail(employeeDto.getEmail());
        employee.setUser(admin);
        System.out.println("add user " + employeeDto.getUserName());
        System.out.println("add password " + employeeDto.getPassword());

        Employee savedEmployee = employeeRepository.save(employee);

        // FIX: Match the DTO field name exactly (userName -> getUserName)
        if (employeeDto.getUserName() != null && !employeeDto.getUserName().isEmpty()) {
            System.out.println("inside the loop");
            User employeeUser = new User();
            employeeUser.setUserName(employeeDto.getUserName()); // Lombok generates getUserName()
            employeeUser.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
            employeeUser.setEmail(employeeDto.getEmail());
            employeeUser.setRole(normalizedRole);
            employeeUser.setEmpId(savedEmployee.getEmpId());
            userRepository.save(employeeUser);
        }
        return savedEmployee;
    }

    // public Employee updateRecord(Integer id, Employee empData) {
    // Employee existing = employeeRepository.findById(id)
    // .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: "
    // + id));

    // if (empData.getEmpName() != null)
    // existing.setEmpName(empData.getEmpName());
    // if (empData.getRole() != null)
    // existing.setRole(empData.getRole());
    // if (empData.getSalary() != null)
    // existing.setSalary(empData.getSalary());
    // if (empData.getPhone() != null)
    // existing.setPhone(empData.getPhone());
    // if (empData.getEmail() != null)
    // existing.setEmail(empData.getEmail());
    // // here
    // return employeeRepository.save(existing);
    // }

    public Employee updateRecord(Long id, EmployeeRegisterDTO empData) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

        if (empData.getEmpName() != null)
            existing.setEmpName(empData.getEmpName());
        if (empData.getRole() != null) {
            String updatedRole = empData.getRole().toUpperCase();
            existing.setRole(updatedRole);
            User user = userRepository.findByEmpId(existing.getEmpId());
            if (user != null) {
                user.setRole(updatedRole);
                userRepository.save(user);
            }
        }
        if (empData.getSalary() != null)
            existing.setSalary(empData.getSalary());
        if (empData.getPhone() != null)
            existing.setPhone(empData.getPhone());
        if (empData.getEmail() != null)
            existing.setEmail(empData.getEmail());
        // here
        if (empData.getUserName() != null) {
            User user = userRepository.findByEmpId(existing.getEmpId());
            user.setUserName(empData.getUserName());
            userRepository.save(user);
        }
        if (empData.getPassword() != null && !empData.getPassword().isEmpty()) {
            User user = userRepository.findByEmpId(existing.getEmpId());
            if (user != null) {
                user.setPassword(passwordEncoder.encode(empData.getPassword()));
                userRepository.save(user);
            }
        }
        return employeeRepository.save(existing);
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteRecord(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot delete. Employee not found."));

        // 1. Delete all salary payments linked to this employee
        List<SalaryPayments> payments = salaryPaymentRepository.findByEmployee_EmpId(id);
        if (payments != null && !payments.isEmpty()) {
            salaryPaymentRepository.deleteAll(payments);
        }

        // 2. Delete the associated login User account if it exists
        User staffUser = userRepository.findByEmpId(id);
        if (staffUser != null) {
            // Remove from admin's expense/bill lists if they created anything (Cascade will
            // handle DB, but this avoids concurrent mod issues)
            userRepository.delete(staffUser);
        }

        // 3. Bidirectional cleanup: remove employee from admin's list
        User admin = employee.getUser();
        if (admin != null && admin.getEmployees() != null) {
            admin.getEmployees().remove(employee);
        }

        // 4. Delete the employee record
        employeeRepository.delete(employee);
    }

    public List<Employee> getAllByUser(Long userId) {
        return employeeRepository.findByUser_UserId(userId);
    }

    public Employee getRecordById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found."));
    }
}
