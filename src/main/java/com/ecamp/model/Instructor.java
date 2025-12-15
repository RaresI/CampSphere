// com/ecamp/model/Instructor.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.util.List;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Instructor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String expertise;

    @ManyToMany(mappedBy = "instructors")
    @JsonIgnore
    private List<Activity> activities;

    @ElementCollection
    private List<DateTimeRange> availability;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getExpertise() { return expertise; }
    public void setExpertise(String e) { this.expertise = e; }
    public List<Activity> getActivities() { return activities; }
    public void setActivities(List<Activity> a) { this.activities = a; }
    public List<DateTimeRange> getAvailability() { return availability; }
    public void setAvailability(List<DateTimeRange> a) { this.availability = a; }
}
