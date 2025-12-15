// com/ecamp/model/Score.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Score {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int points;
    private Instant recordedAt = Instant.now();

    @ManyToOne @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne @JoinColumn(name = "activity_id")
    private Activity activity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getPoints() { return points; }
    public void setPoints(int p) { this.points = p; }
    public Instant getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Instant t) { this.recordedAt = t; }
    public Group getGroup() { return group; }
    public void setGroup(Group g) { this.group = g; }
    public Activity getActivity() { return activity; }
    public void setActivity(Activity a) { this.activity = a; }
}
