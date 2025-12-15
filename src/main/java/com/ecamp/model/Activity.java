// com/ecamp/model/Activity.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.util.List;
import java.time.Duration;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Activity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Duration duration;
    private int capacity;

    @ManyToOne @JoinColumn(name = "camp_id")
    private Camp camp;

    @ManyToMany
    @JoinTable(name = "activity_instructor",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Score> scores;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public Duration getDuration() { return duration; }
    public void setDuration(Duration d) { this.duration = d; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int c) { this.capacity = c; }
    public Camp getCamp() { return camp; }
    public void setCamp(Camp c) { this.camp = c; }
    public List<Instructor> getInstructors() { return instructors; }
    public void setInstructors(List<Instructor> i) { this.instructors = i; }
    public List<Score> getScores() { return scores; }
    public void setScores(List<Score> s) { this.scores = s; }
}
