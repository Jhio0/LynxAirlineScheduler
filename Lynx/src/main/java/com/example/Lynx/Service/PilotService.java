package com.example.Lynx.Service;

import com.example.Lynx.DTO.PilotDTO;
import com.example.Lynx.Entity.Pilot;
import com.example.Lynx.Repository.PilotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PilotService {
    @Autowired
    private PilotRepository pilotRepository;

    // Converts a Pilot entity to PilotDTO
    private PilotDTO convertToDTO(Pilot pilot) {
        PilotDTO pilotDTO = new PilotDTO();
        pilotDTO.setPilotID(pilot.getPilotID());
        pilotDTO.setName(pilot.getName());
        pilotDTO.setEmail(pilot.getEmail());
        pilotDTO.setPassword(pilot.getPassword());
        pilotDTO.setRank(pilot.getRank());
        return pilotDTO;
    }

    // Converts a PilotDTO to a Pilot entity
    private Pilot convertToEntity(PilotDTO pilotDTO) {
        Pilot pilot = new Pilot();
        pilot.setPilotID(pilotDTO.getPilotID());
        pilot.setName(pilotDTO.getName());
        pilot.setEmail(pilotDTO.getEmail());
        pilot.setPassword(pilotDTO.getPassword());
        pilot.setRank(pilotDTO.getRank());
        return pilot;
    }

    public List<PilotDTO> getAllPilots() {
        return pilotRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PilotDTO getPilotById(Long pilotID) {
        return pilotRepository.findById(pilotID)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Pilot not found with ID: " + pilotID));
    }

    public PilotDTO savePilot(PilotDTO pilotDTO) {
        Pilot pilot = convertToEntity(pilotDTO);
        Pilot savedPilot = pilotRepository.save(pilot);
        System.out.println("Saved Pilot ID: " + savedPilot.getPilotID());

        return convertToDTO(savedPilot);
    }


    public void deletePilot(Long pilotID) {
        pilotRepository.deleteById(pilotID);
    }
}
