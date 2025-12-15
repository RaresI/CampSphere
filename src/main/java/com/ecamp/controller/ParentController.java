package com.ecamp.controller;

import com.ecamp.model.Parent;
import com.ecamp.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentRepository repo;

    @GetMapping
    public List<Parent> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Parent getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Parent create(@RequestBody Parent p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Parent update(@PathVariable Long id, @RequestBody Parent p) {
        p.setId(id);
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
