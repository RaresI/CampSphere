package com.ecamp.controller;

import com.ecamp.model.Score;
import com.ecamp.repository.ScoreRepository;
import com.ecamp.repository.GroupRepository;
import com.ecamp.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired private ScoreRepository repo;
    @Autowired private GroupRepository groupRepo;
    @Autowired private ActivityRepository actRepo;

    @GetMapping
    public List<Score> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Score getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Score create(@RequestBody Map<String, Object> b) {
        Score s = new Score();

        // Safely parse groupId and activityId
        Long groupId = Long.valueOf(b.get("groupId").toString());
        Long activityId = Long.valueOf(b.get("activityId").toString());
        int points = Integer.parseInt(b.get("points").toString());

        s.setGroup(groupRepo.findById(groupId).orElseThrow());
        s.setActivity(actRepo.findById(activityId).orElseThrow());
        s.setPoints(points);
        s.setRecordedAt(java.time.Instant.now());
        System.out.println("Payload received: " + b);

        return repo.save(s);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @GetMapping("/byCamp/{campId}")
    public List<Map<String, Object>> getLeaderboard(@PathVariable Long campId) {
        return groupRepo.findAll().stream()
                .filter(g -> g.getCamp().getId().equals(campId))
                .map(g -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("groupName", g.getName());
                    map.put("totalPoints", g.getScores().stream().mapToInt(Score::getPoints).sum());
                    return map;
                })
                .sorted((a, b) -> ((Integer) b.get("totalPoints")).compareTo((Integer) a.get("totalPoints")))
                .toList();
    }


}
