package com.ecamp.model;

import jakarta.persistence.*;

@Entity
public class Trip {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String date;

    private int capacity;         // Max allowed
    private double price;         // Trip cost per child

    @ManyToOne @JoinColumn(name = "camp_id")
    private Camp camp;

    // ─── Getters and Setters ─────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String n) { this.name = n; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    public String getDate() { return date; }
    public void setDate(String d) { this.date = d; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int c) { this.capacity = c; }

    public double getPrice() { return price; }
    public void setPrice(double p) { this.price = p; }

    public Camp getCamp() { return camp; }
    public void setCamp(Camp c) { this.camp = c; }
}
