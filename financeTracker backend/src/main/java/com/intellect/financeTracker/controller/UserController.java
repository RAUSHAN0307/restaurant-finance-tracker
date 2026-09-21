package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.config.JwtProvider;
import com.intellect.financeTracker.dto.AuthResponse;
import com.intellect.financeTracker.dto.UserRegisterRequest;
import com.intellect.financeTracker.model.User;
import com.intellect.financeTracker.service.implementation.AuthService;
import com.intellect.financeTracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // -----------------------------------------------------
    // Migrated from AuthController
    // -----------------------------------------------------
    @PreAuthorize("permitAll()")
    @PostMapping("/register-owner")
    public AuthResponse registerOwner(@RequestBody UserRegisterRequest request) {
        // 1. Save the user to the database
        User user = authService.registerOwner(request);

        // 2. Create an Authentication object for the newly registered user
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                null,
                Collections.emptyList());

        // 3. Generate the JWT token immediately
        String token = JwtProvider.generateToken(authentication);

        // 4. Return the AuthResponse containing the token
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setMessage("Registration successful and logged in");
        response.setUser(user);
        response.setAdminId(0L); // Owner's adminId is 0

        return response;
    }

    // -----------------------------------------------------
    // Standard CRUD
    // -----------------------------------------------------
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
