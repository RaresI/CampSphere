package com.ecamp.controller;

import com.ecamp.model.Camp;
import com.ecamp.model.Registration;
import com.ecamp.repository.CampRepository;
import com.ecamp.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camps")
public class CampController {

    @Autowired
    private CampRepository repo;
    @Autowired
    private RegistrationRepository registrationRepo;

    @GetMapping
    public List<Camp> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Camp getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Camp create(@RequestBody Camp c) {
        return repo.save(c);
    }

    @PutMapping("/{id}")
    public Camp update(@PathVariable Long id, @RequestBody Camp c) {
        c.setId(id);
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @GetMapping("/child/{childId}")
    public List<Camp> getCampsForChild(@PathVariable Long childId) {
        return registrationRepo.findAll().stream()
                .filter(r -> r.getChild().getId().equals(childId))
                .map(Registration::getCamp)
                .distinct()
                .toList();
    }

}
