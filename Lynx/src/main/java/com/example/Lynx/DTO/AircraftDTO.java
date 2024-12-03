package com.example.Lynx.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // Generates constructor with all fields
@NoArgsConstructor
public class AircraftDTO {
    private String aircraftID;
    private String acType;

    // Getters and setters
}