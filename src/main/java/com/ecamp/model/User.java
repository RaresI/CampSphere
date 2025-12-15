package com.ecamp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    // ✅ GETTERS

    public Long getId() { return id; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getPhone() { return phone; }

    public Role getRole() { return role; }

    // ✅ SETTERS

    public void setId(Long id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setRole(Role role) { this.role = role; }
}
