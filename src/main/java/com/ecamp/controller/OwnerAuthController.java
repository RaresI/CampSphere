// src/main/java/com/ecamp/controller/OwnerAuthController.java
package com.ecamp.controller;

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
@RequestMapping("/api/auth/owner")
public class OwnerAuthController {

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request
    ) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // verify ROLE_OWNER
            boolean isOwner = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"));
            if (!isOwner) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("User is not an OWNER");
            }

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    sc
            );

            return ResponseEntity.ok("Owner login successful");
        } catch (AuthenticationException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }
}
