// src/main/java/com/ecamp/controller/DebugController.java
package com.ecamp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/whoami")
    public String whoami(Authentication auth) {
        if (auth == null) {
            return "❌ Not logged in.";
        }
        String roles = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        return "✅ Logged in as: "
                + auth.getName()
                + ", Roles: [" + roles + "]";
    }
}
