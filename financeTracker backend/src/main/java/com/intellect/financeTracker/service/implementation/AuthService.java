package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.dto.UserRegisterRequest;
import com.intellect.financeTracker.model.User;

import com.intellect.financeTracker.repository.UserRepository;
//import com.intellect.financeTracker.serviceImpl.OtpService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Transactional
    // public User registerOwner(UserRegisterRequest request, String otp) {
    // // PRO-CHECK: Validate OTP before doing anything with the DB
    // boolean isValid = otpService.validateOtp(request.getPhoneNumber(), otp);
    // if (!isValid) {
    // throw new RuntimeException("Invalid or expired OTP");
    // }
    //
    // // 1. Create and Save the User
    // User user = new User();
    // user.setUsername(request.getUsername());
    // user.setPassword(passwordEncoder.encode(request.getPassword()));
    // user.setPhoneNumber(request.getPhoneNumber()); // Ensure your User entity has
    // this field
    //
    // user = userRepository.save(user);
    //
    // // Clear OTP after successful use
    // otpService.clearOtp(request.getPhoneNumber());
    //
    // return user;
    // }

    // }

    @Transactional
    public User registerOwner(UserRegisterRequest request) {
        if (userRepository.findFirstByUserName(request.getUsername()).isPresent()) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Username already exists. Please choose a different username.");
        }
        User user = new User();
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        if (request.getRole() == null) {
            user.setRole("OWNER");
        } else {
            user.setRole(request.getRole());
        }
        return userRepository.save(user);
    }
}

// @Transactional
// public Employee createStaff(EmployeeRegisterRequest request) {
//
//
// // 2. Create the Employee record
// Employee employee = new Employee();
// employee.setEmpName(request.getEmpName());
// employee.setEmail(request.getEmail());
// employee.setPhoneNo(request.getPhoneNo());
// employee.setSalary(request.getSalary());
// employee.setRole(request.getRole()); // Role from request (e.g., WAITER,
// CHEF)
// employee.setJoinDate(LocalDate.now());
//
// if(request.getRole().equals("WAITER") || request.getRole().equals("MANAGER"))
// {
// // 1. Create the User for the Staff (so they can login)
//
// User user = new User();
// user.setUsername(request.getUsername());
// user.setPassword(passwordEncoder.encode(request.getPassword()));
// user.setEmail(request.getEmail());
// user = userRepository.save(user);
// // 3. Link to the User account
// employee.setUser(user);
// }
//
//
// return employeeRepository.save(employee);
// }