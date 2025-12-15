// com/ecamp/model/DateTimeRange.java
package com.ecamp.model;

import jakarta.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public class DateTimeRange {
    private Instant startTime;
    private Instant endTime;

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}

