package com.ecamp.controller;

import com.ecamp.model.Location;
import com.ecamp.repository.LocationRepository;
import com.ecamp.repository.CampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired private LocationRepository repo;
    @Autowired private CampRepository campRepo;

    @GetMapping
    public List<Location> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Location getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Location create(@RequestBody Map<String,Object> b) {
        Long campId = Long.valueOf(b.get("campId").toString());
        var camp = campRepo.findById(campId).orElseThrow();
        Location l = new Location();
        l.setName(b.get("name").toString());
        l.setAddress(b.get("address").toString());
        l.setCapacity((int)b.get("capacity"));
        l.setCamp(camp);
        return repo.save(l);
    }

    @PutMapping("/{id}")
    public Location update(@PathVariable Long id, @RequestBody Location l) {
        l.setId(id);
        return repo.save(l);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
