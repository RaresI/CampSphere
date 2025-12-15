package com.ecamp.controller;

import com.ecamp.model.Registration;
import com.ecamp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired private RegistrationRepository repo;
    @Autowired private ChildRepository childRepo;
    @Autowired private CampRepository campRepo;
    @Autowired
    private TripRepository tripRepo;


    @GetMapping
    public List<Registration> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Registration getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> b) {
        try {
            // Validate required fields
            if (!b.containsKey("childId") || b.get("childId") == null) {
                return ResponseEntity.badRequest().body("Please select a child.");
            }
            
            if (!b.containsKey("campId") || b.get("campId") == null) {
                return ResponseEntity.badRequest().body("Please select a camp.");
            }
            
            var r = new Registration();

            // Get child and camp
            var childOpt = childRepo.findById(Long.valueOf(b.get("childId").toString()));
            if (childOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Selected child not found. Please try again.");
            }
            
            var campOpt = campRepo.findById(Long.valueOf(b.get("campId").toString()));
            if (campOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Selected camp not found. Please try again.");
            }
            
            var child = childOpt.get();
            var camp = campOpt.get();
            
            // Check if already registered
            boolean alreadyRegistered = repo.findAll().stream()
                    .anyMatch(reg -> reg.getChild().getId().equals(child.getId()) 
                            && reg.getCamp().getId().equals(camp.getId()));
            
            if (alreadyRegistered) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This child is already registered for this camp.");
            }
            
            r.setChild(child);
            r.setCamp(camp);
            r.setRegistrationDate(java.time.Instant.now());
            r.setTotalCost(new java.math.BigDecimal("0"));
            r.setStatus(b.getOrDefault("status", "PENDING").toString());

            // Trips
            if (b.containsKey("tripIds") && b.get("tripIds") != null) {
                @SuppressWarnings("unchecked")
                List<Integer> rawIds = (List<Integer>) b.get("tripIds");
                List<Long> ids = rawIds.stream().map(Long::valueOf).toList();
                var trips = tripRepo.findAllById(ids);
                r.setTrips(trips);
            }

            Registration saved = repo.save(r);
            return ResponseEntity.ok(saved);
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid child or camp selection.");
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed. Please try again later or contact support.");
        }
    }

    @GetMapping("/byChild/{childId}")
    public List<Registration> getByChild(@PathVariable Long childId) {
        return repo.findAll().stream()
                .filter(r -> r.getChild().getId().equals(childId))
                .toList();
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
