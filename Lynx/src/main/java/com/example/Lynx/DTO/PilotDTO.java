package com.example.Lynx.DTO;
import lombok.*;

@Data
@AllArgsConstructor  // Generates constructor with all fields
@NoArgsConstructor   // Generates no-argument constructor
public class PilotDTO {
    private Long pilotID;
    private String name;
    private String email;
    private String password;
    private String rank;
    // No password for security reasons


}