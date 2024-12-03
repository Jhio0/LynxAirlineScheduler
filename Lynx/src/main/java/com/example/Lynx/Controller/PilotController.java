package com.example.Lynx.Controller;

import com.example.Lynx.DTO.PilotDTO;
import com.example.Lynx.Service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pilots")
public class PilotController {
    @Autowired
    private PilotService pilotService;

    @GetMapping
    public List<PilotDTO> getAllPilots() {
        return pilotService.getAllPilots();
    }

    @GetMapping("/{id}")
    public PilotDTO getPilotById(@PathVariable Long id) {
        return pilotService.getPilotById(id);
    }

    @PostMapping
    public PilotDTO createPilot(@RequestBody PilotDTO pilotDTO) {
        return pilotService.savePilot(pilotDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePilot(@PathVariable Long id) {
        pilotService.deletePilot(id);
    }
}
