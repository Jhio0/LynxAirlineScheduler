package com.example.Lynx.Service;

import com.example.Lynx.DTO.StationDTO;
import com.example.Lynx.Entity.Station;
import com.example.Lynx.Repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    // Converts a Station entity to StationDTO
    private StationDTO convertToDTO(Station station) {
        StationDTO stationDTO = new StationDTO();
        stationDTO.setStationID(station.getStationID());
        stationDTO.setStationName(station.getStationName());
        stationDTO.setStationAddress(station.getStationAddress());
        return stationDTO;
    }

    // Converts a StationDTO to a Station entity
    private Station convertToEntity(StationDTO stationDTO) {
        Station station = new Station();
        station.setStationID(stationDTO.getStationID());
        station.setStationName(stationDTO.getStationName());
        station.setStationAddress(stationDTO.getStationAddress());
        return station;
    }

    // Get all stations as a list of StationDTOs
    public List<StationDTO> getAllStations() {
        return stationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a specific station by its ID
    public StationDTO getStationById(String stationID) {
        return stationRepository.findById(stationID)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Station not found with ID: " + stationID));
    }

    // Save a station (converts from StationDTO to Station entity before saving)
    public StationDTO saveStation(StationDTO stationDTO) {
        Station station = convertToEntity(stationDTO);
        Station savedStation = stationRepository.save(station);
        return convertToDTO(savedStation);
    }

    // Delete a station by its ID
    public void deleteStation(String stationID) {
        stationRepository.deleteById(stationID);
    }
}
