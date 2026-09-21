
package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.config.JwtProvider;
import com.intellect.financeTracker.dto.AuthResponse;
import com.intellect.financeTracker.dto.LoginRequest;
import com.intellect.financeTracker.model.User;
import com.intellect.financeTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody(required = false) LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("error", "Username and password cannot be empty"));
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            String token = JwtProvider.generateToken(authentication);

            User user = userRepository.findFirstByUserName(authentication.getName()).orElse(null);

            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setMessage("Login successful");
            response.setUser(user);

            if (user == null) {
                // Handle super admin (defined in properties)
                response.setAdminId(0L);
            } else {
                String role = user.getRole() != null ? user.getRole().toUpperCase() : "";
                if (role.contains("OWNER") || role.contains("ADMIN")) {
                    response.setAdminId(0L);
                } else if (role.contains("MANAGER")) {
                    java.util.List<Long> adminIds = userRepository.findAdminIdsByEmail(user.getEmail());
                    Long adminId = (adminIds != null && !adminIds.isEmpty()) ? adminIds.get(0) : null;
                    // System.out.println("DEBUG: Role contains MANAGER. Fetched adminId from DB
                    // using email ("
                    // + user.getEmail() + "): " + adminId)
                    response.setAdminId(adminId);
                } else if (role.contains("WAITER")) {
                    java.util.List<Long> adminIds = userRepository.findWaiterIdsByEmail(user.getEmail());
                    Long adminId = (adminIds != null && !adminIds.isEmpty()) ? adminIds.get(0) : null;
                    // System.out.println("DEBUG: Role contains WAITER. Fetched waiterId from DB
                    // using email ("
                    // + user.getEmail() + "): " + waiterId)
                    response.setAdminId(adminId);
                }
            }

            return ResponseEntity.ok(response);
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Collections.singletonMap("error", "Invalid username or password"));
        }
    }
}