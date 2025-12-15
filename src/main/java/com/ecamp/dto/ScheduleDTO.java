package com.ecamp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleDTO {
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public String groupName;
    public String activityName;
    public Long instructorId;

    public ScheduleDTO(LocalDate date, LocalTime startTime, LocalTime endTime,
                       String groupName, String activityName, Long instructorId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.groupName = groupName;
        this.activityName = activityName;
        this.instructorId = instructorId;
    }
}
