package com.example.Lynx.Service;

import com.example.Lynx.DTO.FlightDTO;
import com.example.Lynx.Entity.Flight;
import com.example.Lynx.Repository.AircraftRepository;
import com.example.Lynx.Repository.FlightRepository;
import com.example.Lynx.Repository.PilotRepository;
import com.example.Lynx.Repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PilotRepository pilotRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private StationRepository stationRepository;


    // Method to convert Flight entity to FlightDTO
    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightID(flight.getFlightID());
        flightDTO.setPilotID(flight.getPilot().getPilotID());
        flightDTO.setAircraftID(flight.getAircraft().getAircraftID());
        flightDTO.setDate(flight.getDate());
        flightDTO.setPairingActivity(flight.getPairingActivity());
        flightDTO.setDutyReportTime(flight.getDutyReportTime());
        flightDTO.setDepartureStation(flight.getDepartureStation());
        flightDTO.setDepartureStation(flight.getArrivalStation());
        flightDTO.setDutyDebriefEnd(flight.getDutyDebriefEnd());
        flightDTO.setFlyingHours(flight.getFlyingHours());
        flightDTO.setDutyHours(flight.getDutyHours());
        flightDTO.setHotel(flight.getHotel());
        flightDTO.setUpdatedBy(flight.getUpdatedBy());
        flightDTO.setFile(flight.getFile());
        return flightDTO;
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long flightID) {
        return flightRepository.findById(flightID).map(this::convertToDTO).orElse(null);
    }

    public FlightDTO saveFlight(FlightDTO flightDTO) {
        Flight flight = new Flight();
        // Map fields from FlightDTO to Flight entity
        flight.setDate(flightDTO.getDate());
        flight.setPairingActivity(flightDTO.getPairingActivity());
        flight.setDutyReportTime(flightDTO.getDutyReportTime());
        flight.setDepartureStation(flightDTO.getDepartureStation());
        flight.setArrivalStation(flightDTO.getArrivalStation());
        flight.setDutyDebriefEnd(flightDTO.getDutyDebriefEnd());
        flight.setFlyingHours(flightDTO.getFlyingHours());
        flight.setDutyHours(flightDTO.getDutyHours());
        flight.setHotel(flightDTO.getHotel());
        flight.setUpdatedBy(flightDTO.getUpdatedBy());

        // Fetch and set related entities
        flight.setPilot(pilotRepository.findById(flightDTO.getPilotID()).orElse(null));
        flight.setAircraft(aircraftRepository.findById(flightDTO.getAircraftID()).orElse(null));

        Flight savedFlight = flightRepository.save(flight);
        return convertToDTO(savedFlight);
    }

    public void deleteFlight(Long flightID) {
        flightRepository.deleteById(flightID);
    }
}
