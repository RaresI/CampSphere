package com.ecamp.controller;

import com.ecamp.dto.ChildDTO;
import com.ecamp.model.Child;
import com.ecamp.model.Parent;
import com.ecamp.repository.ChildRepository;
import com.ecamp.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    @Autowired private ChildRepository childRepo;
    @Autowired private ParentRepository parentRepo;

    // 1) GET /api/children → include parentId
    @GetMapping
    public List<ChildDTO> list() {
        return childRepo.findAll().stream()
                .map(c -> new ChildDTO(
                        c.getId(),
                        c.getName(),
                        c.getSchool(),
                        c.getParent().getId(),
                        c.getDateOfBirth() != null ? c.getDateOfBirth().toString() : null,
                        c.getMedicalInfo(),
                        c.getEmail()
                ))
                .toList();
    }

    // 2) GET /api/children/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ChildDTO> getOne(@PathVariable Long id) {
        return childRepo.findById(id)
                .map(c -> ResponseEntity.ok(new ChildDTO(
                        c.getId(),
                        c.getName(),
                        c.getSchool(),
                        c.getParent().getId(),
                        c.getDateOfBirth() != null ? c.getDateOfBirth().toString() : null,
                        c.getMedicalInfo(),
                        c.getEmail()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // 3) POST /api/children
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Child c) {
        // Validate parent
        if (c.getParent() == null || c.getParent().getId() == null) {
            return ResponseEntity.badRequest().body("Parent information is required.");
        }

        Optional<Parent> parentOpt = parentRepo.findById(c.getParent().getId());
        if (parentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Parent not found. Please log in again.");
        }

        // Validate required fields
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Child's name is required.");
        }
        
        if (c.getEmail() == null || c.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Child's email is required.");
        }
        
        if (c.getPassword() == null || c.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }
        
        if (c.getDateOfBirth() == null) {
            return ResponseEntity.badRequest().body("Date of birth is required.");
        }
        
        if (c.getSchool() == null || c.getSchool().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("School name is required.");
        }

        try {
            c.setRole(com.ecamp.model.Role.CHILD);
            c.setPassword(new BCryptPasswordEncoder().encode(c.getPassword()));
            c.setParent(parentOpt.get());

            if (c.getPhone() == null || c.getPhone().isBlank()) {
                c.setPhone("0000000000");
            }

            Child saved = childRepo.save(c);
            
            // return the DTO with parentId
            ChildDTO dto = new ChildDTO(
                    saved.getId(),
                    saved.getName(),
                    saved.getSchool(),
                    saved.getParent().getId(),
                    saved.getDateOfBirth() != null ? saved.getDateOfBirth().toString() : null,
                    saved.getMedicalInfo(),
                    saved.getEmail()
            );
            return ResponseEntity.ok(dto);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // Handle duplicate email or other constraint violations
            if (e.getMessage().contains("email")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This email is already registered. Please use a different email.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to add child due to invalid data. Please check the information.");
            }
        } catch (Exception e) {
            System.err.println("Error adding child: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add child. Please try again later.");
        }
    }

    // 4) PUT /api/children/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ChildDTO> update(
            @PathVariable Long id,
            @RequestBody Child c
    ) {
        if (!childRepo.existsById(id))
            return ResponseEntity.notFound().build();

        c.setId(id);
        Child saved = childRepo.save(c);
        ChildDTO dto = new ChildDTO(
                saved.getId(),
                saved.getName(),
                saved.getSchool(),
                saved.getParent().getId(),
                saved.getDateOfBirth() != null ? saved.getDateOfBirth().toString() : null,
                saved.getMedicalInfo(),
                saved.getEmail()
        );
        return ResponseEntity.ok(dto);
    }

    // 5) DELETE /api/children/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!childRepo.existsById(id))
            return ResponseEntity.notFound().build();
        childRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
