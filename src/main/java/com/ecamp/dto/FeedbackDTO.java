package com.ecamp.dto;

import java.time.Instant;

public class FeedbackDTO {
    public Long id;
    public String comment;
    public Instant submittedAt;
    public String childName;
    public String campName;

    public FeedbackDTO(Long id, String comment, Instant submittedAt, String childName, String campName) {
        this.id = id;
        this.comment = comment;
        this.submittedAt = submittedAt;
        this.childName = childName;
        this.campName = campName;
    }
}
