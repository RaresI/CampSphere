// com/ecamp/model/Location.java
package com.ecamp.model;

import jakarta.persistence.*;

@Entity
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private int capacity;

    @ManyToOne @JoinColumn(name = "camp_id")
    private Camp camp;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getAddress() { return address; }
    public void setAddress(String a) { this.address = a; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int c) { this.capacity = c; }
    public Camp getCamp() { return camp; }
    public void setCamp(Camp c) { this.camp = c; }
}
