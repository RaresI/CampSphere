package com.ecamp.dto;

import java.time.Instant;

public class PhotoDTO {
    public Long id;
    public String filePath;
    public Instant uploadedAt;
    public Long feedbackId;
    public String campName;

    public PhotoDTO(Long id, String filePath, Instant uploadedAt, Long feedbackId, String campName) {
        this.id = id;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
        this.feedbackId = feedbackId;
        this.campName = campName;
    }
}
