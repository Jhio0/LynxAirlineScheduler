package com.example.Lynx.Repository;

import com.example.Lynx.Entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, String> {
    // Custom query methods can be added here if needed
    // For example, finding a station by its name
    Station findByStationName(String stationName);
}
