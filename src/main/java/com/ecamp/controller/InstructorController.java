package com.ecamp.controller;

import com.ecamp.model.Instructor;
import com.ecamp.repository.InstructorRepository;
import com.ecamp.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    @Autowired private InstructorRepository repo;
    @Autowired private ActivityRepository actRepo;

    @GetMapping
    public List<Instructor> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Instructor getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Instructor create(@RequestBody Instructor i) {
        return repo.save(i);
    }

    @PutMapping("/{id}")
    public Instructor update(@PathVariable Long id, @RequestBody Instructor i) {
        i.setId(id);
        return repo.save(i);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @PostMapping("/{id}/assign-activities")
    public Instructor assign(@PathVariable Long id, @RequestBody Map<String,List<Long>> b) {
        var instr = repo.findById(id).orElseThrow();
        var acts = b.get("activityIds").stream()
                .map(actRepo::findById)
                .map(opt->opt.orElseThrow())
                .collect(Collectors.toList());
        instr.setActivities(acts);
        return repo.save(instr);
    }
}
