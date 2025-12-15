// src/main/java/com/ecamp/config/DataInitializer.java
package com.ecamp.config;

import com.ecamp.model.Admin;
import com.ecamp.model.Owner;
import com.ecamp.model.Role;
import com.ecamp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("▶️ DataInitializer running…");

        // ADMIN
        String adminEmail = "admin@ecamp.com";
        if (userRepo.findByEmail(adminEmail).isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setPassword(encoder.encode("Admin123!"));
            admin.setPhone("0700000000"); // <- 🔧 Fix
            admin.setRole(Role.ADMIN);
            userRepo.save(admin);
            System.out.println("   ➕ Created ADMIN: " + adminEmail);
        }

        // OWNER
        String ownerEmail = "owner@ecamp.com";
        if (userRepo.findByEmail(ownerEmail).isEmpty()) {
            Owner owner = new Owner();
            owner.setEmail(ownerEmail);
            owner.setPassword(encoder.encode("Owner123!"));
            owner.setPhone("0711111111"); // <- 🔧 Fix
            owner.setRole(Role.OWNER);
            userRepo.save(owner);
            System.out.println("   ➕ Created OWNER: " + ownerEmail);
        }
    }

}
