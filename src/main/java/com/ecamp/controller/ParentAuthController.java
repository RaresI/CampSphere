package com.ecamp.controller;

import com.ecamp.model.Parent;
import com.ecamp.model.Role;
import com.ecamp.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/parents")
public class ParentAuthController {

    @Autowired
    private ParentRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> registerParent(@RequestBody Parent parent) {
        // Validate required fields
        if (parent.getName() == null || parent.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required");
        }
        if (parent.getEmail() == null || parent.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (parent.getPhone() == null || parent.getPhone().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required");
        }
        if (parent.getPassword() == null || parent.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }
        
        // Check if email already exists
        if (repo.findByEmail(parent.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This email is already registered. Please use a different email or try logging in.");
        }
        
        try {
            // Encrypt password
            parent.setPassword(passwordEncoder.encode(parent.getPassword()));
            // Explicitly set role
            parent.setRole(Role.PARENT);
            // Save to database
            repo.save(parent);
            
            return ResponseEntity.ok("Parent registered successfully.");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // Handle duplicate email or other constraint violations
            if (e.getMessage().contains("email")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This email is already registered. Please use a different email or try logging in.");
            } else if (e.getMessage().contains("phone")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This phone number is already registered. Please use a different phone number.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Registration failed due to invalid data. Please check your information and try again.");
            }
        } catch (Exception e) {
            // Log the actual error for debugging (you should use a proper logger)
            System.err.println("Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed. Please try again later or contact support if the problem persists.");
        }
    }


    // Stateless login returns { id, name, email }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String phone
    ) {
        // Validate input
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (phone == null || phone.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required");
        }
        
        return repo.findByEmail(email)
                .map(parent -> {
                    if (parent.getPhone().equals(phone)) {
                        return ResponseEntity.ok(Map.of(
                                "id",    parent.getId(),
                                "name",  parent.getName(),
                                "email", parent.getEmail()
                        ));
                    } else {
                        return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body("Incorrect phone number. Please check your credentials and try again.");
                    }
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("No account found with this email. Please check your email or register a new account."));
    }
}
