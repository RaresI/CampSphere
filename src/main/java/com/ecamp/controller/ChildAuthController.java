// src/main/java/com/ecamp/controller/ChildAuthController.java
package com.ecamp.controller;

import com.ecamp.model.Child;
import com.ecamp.repository.ChildRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/child")
public class ChildAuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private ChildRepository childRepo;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request
    ) {
        try {
            // 1) authenticate
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // 2) store in SecurityContext
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            // 3) manually save SecurityContext into HTTP session
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    context
            );

            return ResponseEntity.ok("Child login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }
    /** returns the currently logged-in child’s basic info */
    @GetMapping("/me")
    public ResponseEntity<Child> me(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = auth.getName();
        return childRepo
                .findByEmail(email)
                .map(child -> ResponseEntity.ok(child))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
