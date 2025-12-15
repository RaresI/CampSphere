package com.ecamp.controller;

import com.ecamp.model.Activity;
import com.ecamp.repository.ActivityRepository;
import com.ecamp.repository.CampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired private ActivityRepository repo;
    @Autowired private CampRepository campRepo;

    @GetMapping
    public List<Activity> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Activity getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Activity create(@RequestBody Map<String,Object> b) {
        Long campId = Long.valueOf(b.get("campId").toString());
        var camp = campRepo.findById(campId).orElseThrow();
        Activity a = new Activity();
        a.setName(b.get("name").toString());
        a.setDescription(b.get("description").toString());
        a.setDuration(java.time.Duration.parse(b.get("duration").toString()));
        a.setCapacity((int)b.get("capacity"));
        a.setCamp(camp);
        return repo.save(a);
    }

    @PutMapping("/{id}")
    public Activity update(@PathVariable Long id, @RequestBody Activity a) {
        a.setId(id);
        return repo.save(a);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @GetMapping("/byCamp/{campId}")
    public List<Activity> getByCamp(@PathVariable Long campId) {
        return repo.findAll().stream()
                .filter(a -> a.getCamp() != null && a.getCamp().getId().equals(campId))
                .toList();
    }

}
