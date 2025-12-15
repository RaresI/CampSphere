package com.ecamp.controller;

import com.ecamp.model.Trip;
import com.ecamp.repository.RegistrationRepository;
import com.ecamp.repository.TripRepository;
import com.ecamp.repository.CampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired private TripRepository repo;
    @Autowired private CampRepository campRepo;

    @GetMapping
    public List<Trip> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Trip getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Trip create(@RequestBody Map<String, Object> b) {
        if (!b.containsKey("campId") || !b.containsKey("name")) {
            throw new IllegalArgumentException("campId and name are required");
        }

        Long campId = Long.parseLong(b.get("campId").toString());
        var camp = campRepo.findById(campId).orElseThrow(() -> new RuntimeException("Camp not found"));

        Trip t = new Trip();
        t.setName(b.get("name").toString());
        t.setDescription(b.getOrDefault("description", "").toString());
        t.setDate(b.getOrDefault("date", "").toString());
        t.setCapacity(Integer.parseInt(b.getOrDefault("capacity", "0").toString()));
        t.setPrice(Double.parseDouble(b.getOrDefault("price", "0.0").toString()));
        t.setCamp(camp);

        return repo.save(t);
    }

    @GetMapping("/byCamp/{campId}")
    public List<Trip> getByCamp(@PathVariable Long campId) {
        return repo.findByCampId(campId);
    }



    @PutMapping("/{id}")
    public Trip update(@PathVariable Long id, @RequestBody Trip t) {
        t.setId(id);
        return repo.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @Autowired private RegistrationRepository registrationRepo;

//    @PostMapping("/auto-cancel")
//    public String autoCancelTrips(@RequestParam Long campId) {
//        var camp = campRepo.findById(campId).orElseThrow();
//        var trips = repo.findByCampId(campId);
//
//        int canceledCount = 0;
//
//        for (Trip trip : trips) {
//            long signups = registrationRepo.findByCamp(camp).stream()
//                    .filter(r -> r.getTrips().contains(trip))
//                    .count();
//
//            if (signups < trip.getMinParticipants()) {
//                trip.setCanceled(true);
//                repo.save(trip);
//                canceledCount++;
//            }
//        }
//
//        return canceledCount + " trips were auto-canceled.";
//    }
//
//    @PutMapping("/{id}/cancel")
//    public Trip cancelTrip(@PathVariable Long id) {
//        Trip trip = repo.findById(id).orElseThrow();
//        trip.setCanceled(true);
//        return repo.save(trip);
//    }


}
