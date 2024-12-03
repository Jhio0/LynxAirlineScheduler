package com.example.Lynx.Repository;

import com.example.Lynx.Entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, String> {
    // You can add custom query methods here if needed
}