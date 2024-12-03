package com.example.Lynx.Service;

import com.example.Lynx.DTO.AircraftDTO;
import com.example.Lynx.Entity.Aircraft;
import com.example.Lynx.Repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    // Convert Aircraft entity to AircraftDTO
    private AircraftDTO convertToDTO(Aircraft aircraft) {
        AircraftDTO dto = new AircraftDTO();
        dto.setAircraftID(aircraft.getAircraftID());
        dto.setAcType(aircraft.getAcType());
        // Add more fields as needed
        return dto;
    }

    // Convert AircraftDTO to Aircraft entity
    private Aircraft convertToEntity(AircraftDTO dto) {
        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftID(dto.getAircraftID());
        aircraft.setAcType(dto.getAcType());
        // Add more fields as needed
        return aircraft;
    }

    // Create or update an Aircraft
    public AircraftDTO saveAircraft(AircraftDTO aircraftDTO) {
        Aircraft aircraft = convertToEntity(aircraftDTO);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return convertToDTO(savedAircraft);
    }

    // Get all Aircrafts
    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get Aircraft by ID
    public Optional<AircraftDTO> getAircraftById(String aircraftID) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftID);
        return aircraft.map(this::convertToDTO);
    }

    // Delete Aircraft by ID
    public void deleteAircraft(String aircraftID) {
        aircraftRepository.deleteById(aircraftID);
    }

    // Additional methods for business logic (if needed)
}
