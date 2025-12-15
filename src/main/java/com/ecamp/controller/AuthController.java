package com.ecamp.controller;

import com.ecamp.model.Parent;
import com.ecamp.model.Role;
import com.ecamp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo; // ✅ NOT ParentRepository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/parent")
    public ResponseEntity<String> register(@RequestBody Parent parent) {
        System.out.println("== DEBUG REGISTER ==");
        System.out.println("Name: " + parent.getName());
        System.out.println("Email: " + parent.getEmail());
        System.out.println("Phone: " + parent.getPhone());
        System.out.println("Password: " + parent.getPassword());

        if (parent.getEmail() == null || parent.getPassword() == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        parent.setPassword(passwordEncoder.encode(parent.getPassword()));
        parent.setRole(Role.PARENT);
        userRepo.save(parent);

        return ResponseEntity.ok("Parent registered");
    }




}
