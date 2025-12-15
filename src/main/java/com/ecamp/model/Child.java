// com/ecamp/model/Child.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Child extends User{
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String name;
    private String dateOfBirth;
    private String school;
    private String medicalInfo;

    @ManyToOne @JoinColumn(name = "parent_id")
    private Parent parent;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Registration> registrations;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Feedback> feedbacks;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public String getMedicalInfo() { return medicalInfo; }
    public void setMedicalInfo(String medicalInfo) { this.medicalInfo = medicalInfo; }
    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }
    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> regs) { this.registrations = regs; }
    public List<Feedback> getFeedbacks() { return feedbacks; }
    public void setFeedbacks(List<Feedback> fbs) { this.feedbacks = fbs; }
}
