// com/ecamp/model/Camp.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Camp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private String description;
    private int capacity;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL) @JsonIgnore
    private List<Registration> registrations;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL) @JsonIgnore
    private List<Group> groups;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL) @JsonIgnore
    private List<Location> heldAt;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL) @JsonIgnore
    private List<Trip> offers;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL) @JsonIgnore
    private List<Activity> activities;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL) @JsonIgnore
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL) @JsonIgnore
    private List<Photo> photos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String d) { this.startDate = d; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String d) { this.endDate = d; }
    public String getDescription() { return description; }
    public void setDescription(String desc) { this.description = desc; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> regs) { this.registrations = regs; }
    public List<Group> getGroups() { return groups; }
    public void setGroups(List<Group> groups) { this.groups = groups; }
    public List<Location> getHeldAt() { return heldAt; }
    public void setHeldAt(List<Location> locs) { this.heldAt = locs; }
    public List<Trip> getOffers() { return offers; }
    public void setOffers(List<Trip> offers) { this.offers = offers; }
    public List<Activity> getActivities() { return activities; }
    public void setActivities(List<Activity> acts) { this.activities = acts; }
    public List<Feedback> getFeedbacks() { return feedbacks; }
    public void setFeedbacks(List<Feedback> fbs) { this.feedbacks = fbs; }
    public List<Photo> getPhotos() { return photos; }
    public void setPhotos(List<Photo> photos) { this.photos = photos; }
}
