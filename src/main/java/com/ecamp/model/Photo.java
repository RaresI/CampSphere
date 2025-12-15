// com/ecamp/model/Photo.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Photo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private Instant uploadedAt = Instant.now();

    @ManyToOne @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @ManyToOne @JoinColumn(name = "camp_id")
    private Camp camp;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String f) { this.filePath = f; }
    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant t) { this.uploadedAt = t; }
    public Feedback getFeedback() { return feedback; }
    public void setFeedback(Feedback f) { this.feedback = f; }
    public Camp getCamp() { return camp; }
    public void setCamp(Camp c) { this.camp = c; }
}
