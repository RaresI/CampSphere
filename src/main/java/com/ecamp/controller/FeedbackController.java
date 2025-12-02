package com.ecamp.controller;

import com.ecamp.dto.FeedbackDTO;
import com.ecamp.model.Feedback;
import com.ecamp.repository.FeedbackRepository;
import com.ecamp.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired private FeedbackRepository repo;
    @Autowired private ChildRepository childRepo;

    @GetMapping
    public List<FeedbackDTO> list(@RequestParam(required = false) Long campId) {
        List<Feedback> feedbacks;
        if (campId != null) {
            feedbacks = repo.findAll().stream()
                .filter(f -> f.getCamp().getId().equals(campId))
                .toList();
        } else {
            feedbacks = repo.findAll();
        }
        return feedbacks.stream().map(f -> new FeedbackDTO(
                f.getId(),
                f.getComment(),
                f.getSubmittedAt(),
                f.getChild().getName(),
                f.getCamp().getName()
        )).toList();
    }


    @GetMapping("/{id}")
    public Feedback getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Feedback create(@RequestBody Feedback f) {
        f.setChild(childRepo.findById(f.getChild().getId()).orElseThrow());
        f.setSubmittedAt(java.time.Instant.now());
        return repo.save(f);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
