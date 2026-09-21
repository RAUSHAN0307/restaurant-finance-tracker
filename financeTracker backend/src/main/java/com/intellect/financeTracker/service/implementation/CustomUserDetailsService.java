package com.intellect.financeTracker.service.implementation;

// import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.model.User;
import com.intellect.financeTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${superAdminUsername}")
    private String superAdminUsername;

    @Value("${superAdminPassword}")
    private String superAdminPassword;

    // @Override
    // public UserDetails loadUserByUsername(String username)
    // throws UsernameNotFoundException {
    //
    // // SUPER ADMIN LOGIN
    // if (username.equals(superAdminUsername)) {
    // return org.springframework.security.core.userdetails.User
    // .withUsername(superAdminUsername)
    // .password(passwordEncoder.encode(superAdminPassword))
    // .roles("SUPER_ADMIN")
    // .build();
    // }
    //
    // // DATABASE USER
    // User user = userRepository.findByUsername(username)
    // .orElseThrow(() -> new UserNotFoundException("User not found"));
    //
    //// return org.springframework.security.core.userdetails.User
    //// .withUsername(user.getUsername())
    //// .password(user.getPassword())
    //// .build();
    //
    // return org.springframework.security.core.userdetails.User
    // .withUsername(user.getUsername())
    // .password(user.getPassword())
    // .roles(user.getEmployee().getRole().name())
    // .build();
    // }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. SUPER ADMIN LOGIN
        if (username.equals(superAdminUsername)) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(superAdminUsername)
                    // Use the raw password from property if your encoder matches
                    // Better: Have it already encoded in your properties file
                    .password(passwordEncoder.encode(superAdminPassword))
                    .roles("ADMIN")
                    .build();
        }

        // 2. DATABASE USER
        User user = userRepository.findFirstByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. GET ROLE FROM THE DATABASE
        String role = user.getRole();
        if (role == null || role.trim().isEmpty()) {
            role = "USER"; // Default fallback
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}