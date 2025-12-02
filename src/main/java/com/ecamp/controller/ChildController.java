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
    public ResponseEntity<ChildDTO> create(@RequestBody Child c) {
        if (c.getParent()==null || c.getParent().getId()==null)
            return ResponseEntity.badRequest().build();

        Optional<Parent> parentOpt = parentRepo.findById(c.getParent().getId());
        if (parentOpt.isEmpty())
            return ResponseEntity.badRequest().build();

        if (c.getEmail()==null || c.getPassword()==null)
            return ResponseEntity.badRequest().build();

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
