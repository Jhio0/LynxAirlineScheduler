package com.example.Lynx.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // Generates constructor with all fields
@NoArgsConstructor   // Generates no-argument constructor
public class StationDTO {
    private String stationID;
    private String stationName;
    private String stationAddress;

    // Getters and setters
}