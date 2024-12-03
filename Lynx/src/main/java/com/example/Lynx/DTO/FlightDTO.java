package com.example.Lynx.DTO;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor  // Generates constructor with all fields
@NoArgsConstructor   // Generates no-argument constructor
public class FlightDTO {
    private Long flightID;
    private Long pilotID;
    private String aircraftID;
    private LocalDate date;
    private String pairingActivity;
    private LocalTime dutyReportTime;
    private String departureStation; // Changed field name to be clear
    private String arrivalStation;   // Changed field name to be clear
    private LocalTime dutyDebriefEnd;
    private LocalTime flyingHours;
    private LocalTime dutyHours;
    private String hotel;
    private String updatedBy;
    private LocalDate updatedDate;
    private String file;
}
