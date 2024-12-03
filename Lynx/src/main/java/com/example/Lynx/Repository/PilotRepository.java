package com.example.Lynx.Repository;

import com.example.Lynx.Entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Long> {

    // Custom query methods can be added here if needed
    // Example: Find a pilot by email
    Optional<Pilot> findByEmail(String email);
}