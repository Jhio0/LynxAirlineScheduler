package com.example.Lynx.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Table(name = "pilot")
@Data
@AllArgsConstructor  // Generates constructor with all fields
@NoArgsConstructor   // Generates no-argument constructor
public class Pilot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pilotid")
    private Long pilotID;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "rank")// Store hashed passwords
    private String rank;

    @OneToMany(mappedBy = "pilot")
    private List<Flight> flights;

    // Getters and Setters
}
