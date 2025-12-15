package com.ecamp.controller;

import com.ecamp.dto.ScheduleDTO;
import com.ecamp.model.DailySchedule;
import com.ecamp.repository.DailyScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import com.ecamp.model.Instructor;
import com.ecamp.repository.InstructorRepository;


import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class DailyScheduleController {

    @Autowired private DailyScheduleRepository repo;
    @Autowired private InstructorRepository instructorRepo;


    @GetMapping
    public List<DailySchedule> list() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DailySchedule sched) {
        if (sched.getInstructorId() == null || sched.getDate() == null || sched.getStartTime() == null || sched.getEndTime() == null) {
            return ResponseEntity.badRequest().body("Missing required scheduling info.");
        }

        // Load instructor
        Instructor instructor = instructorRepo.findById(sched.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        // Convert start/end to Instant for comparison
        LocalDate date = sched.getDate();
        Instant desiredStart = date.atTime(sched.getStartTime()).atZone(ZoneId.systemDefault()).toInstant();
        Instant desiredEnd = date.atTime(sched.getEndTime()).atZone(ZoneId.systemDefault()).toInstant();

        // Validate availability
        boolean available = instructor.getAvailability().stream().anyMatch(range ->
                !desiredStart.isBefore(range.getStartTime()) &&
                        !desiredEnd.isAfter(range.getEndTime())
        );

        if (!available) {
            return ResponseEntity.status(409).body("Instructor is not available at that time.");
        }

        // Check for conflicts
        List<DailySchedule> existing = repo.findByInstructorIdAndDate(sched.getInstructorId(), sched.getDate());
        boolean conflict = existing.stream().anyMatch(existingSched ->
                timesOverlap(sched.getStartTime(), sched.getEndTime(), existingSched.getStartTime(), existingSched.getEndTime())
        );

        if (conflict) {
            return ResponseEntity.status(409).body("Instructor already booked for this time slot.");
        }

        // Save schedule
        DailySchedule saved = repo.save(sched);
        return ResponseEntity.ok(saved);
    }


    // Utility method
    private boolean timesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }


    @GetMapping("/byCamp/{campId}")
    public List<ScheduleDTO> getByCamp(@PathVariable Long campId) {
        return repo.findByCampIdOrderByDateAscStartTimeAsc(campId)
                .stream()
                .map(s -> new ScheduleDTO(
                        s.getDate(),
                        s.getStartTime(),
                        s.getEndTime(),
                        s.getGroup() != null ? s.getGroup().getName() : "—",
                        s.getActivity() != null ? s.getActivity().getName() : "—",
                        s.getInstructorId()
                ))
                .toList();
    }

}
