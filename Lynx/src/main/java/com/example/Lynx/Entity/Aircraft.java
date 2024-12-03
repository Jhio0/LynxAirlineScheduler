package com.example.Lynx.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "aircraft")
@Data
public class Aircraft {
    @Id
    @Column(name = "aircraftid")
    private String aircraftID;

    @Column(name = "ac_type")
    private String acType;

    @OneToMany(mappedBy = "aircraft")
    private List<Flight> flights;

    // Getters and Setters
}
