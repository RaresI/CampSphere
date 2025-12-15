// com/ecamp/model/Group.java
package com.ecamp.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "camp_group")
public class Group {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne @JoinColumn(name = "camp_id")
    private Camp camp;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Child> children;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Score> scores;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public Camp getCamp() { return camp; }
    public void setCamp(Camp c) { this.camp = c; }
    public List<Child> getChildren() { return children; }
    public void setChildren(List<Child> ch) { this.children = ch; }
    public List<Score> getScores() { return scores; }
    public void setScores(List<Score> s) { this.scores = s; }
}
