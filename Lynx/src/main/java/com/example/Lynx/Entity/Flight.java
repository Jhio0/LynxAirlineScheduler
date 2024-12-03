package com.example.Lynx.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "flight")
@Data
@AllArgsConstructor  // Generates constructor with all fields
@NoArgsConstructor   // Generates no-argument constructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flightid")
    private Long flightID;

    @ManyToOne
    @JoinColumn(name = "pilotID", nullable = false)
    private Pilot pilot;

    @ManyToOne
    @JoinColumn(name = "aircraftID", nullable = false)
    private Aircraft aircraft;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "pairingactivity")
    private String pairingActivity;

    @Column(name = "dutyreporttime")
    private LocalTime dutyReportTime;

    @Column(name = "depstn")
    private String departureStation;

    @Column(name = "arrstn")
    private String arrivalStation;

    @Column(name = "dutydebriefend")
    private LocalTime dutyDebriefEnd;

    @Column(name = "flyinghours")
    private LocalTime flyingHours;

    @Column(name = "dutyhours")
    private LocalTime dutyHours;

    @Column(name = "hotel")
    private String hotel;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "updateddate")
    private LocalDate updatedDate;

    @Column(name = "file")
    private String file;

    // Getters and Setters
}
