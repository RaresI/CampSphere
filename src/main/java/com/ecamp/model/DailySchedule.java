package com.ecamp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class DailySchedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Camp camp;
    @ManyToOne private Group group;      // nullable if it's a global activity
    @ManyToOne private Activity activity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    // optional: future instructor assignment
    private Long instructorId; // simplified, or make it @ManyToOne Instructor

    public Long getId() { return id; }
    public Camp getCamp() { return camp; }
    public void setCamp(Camp camp) { this.camp = camp; }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public Activity getActivity() { return activity; }
    public void setActivity(Activity activity) { this.activity = activity; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
}
