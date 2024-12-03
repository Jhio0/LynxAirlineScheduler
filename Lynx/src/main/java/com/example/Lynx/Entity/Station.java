package com.example.Lynx.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "station")
@Data
public class Station {
    @Id
    @Column(name = "stationid")
    private String stationID; // Using String as per your requirement

    @Column(name = "stationname")
    private String stationName;

    @Column(name = "stationaddress")
    private String stationAddress;

    @OneToMany(mappedBy = "departureStation")
    private List<Flight> departureFlights;

    @OneToMany(mappedBy = "arrivalStation")
    private List<Flight> arrivalFlights;

    // Getters and Setters
}
