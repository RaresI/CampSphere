// com/ecamp/model/Feedback.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
public class Feedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Instant submittedAt = Instant.now();

    @ManyToOne @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne @JoinColumn(name = "camp_id")
    private Camp camp;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL)
    private List<Photo> photos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getComment() { return comment; }
    public void setComment(String c) { this.comment = c; }
    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant t) { this.submittedAt = t; }
    public Child getChild() { return child; }
    public void setChild(Child c) { this.child = c; }
    public Camp getCamp() { return camp; }
    public void setCamp(Camp c) { this.camp = c; }
    public List<Photo> getPhotos() { return photos; }
    public void setPhotos(List<Photo> p) { this.photos = p; }
}
