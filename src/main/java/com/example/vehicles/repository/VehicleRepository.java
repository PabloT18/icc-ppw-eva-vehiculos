package com.example.vehicles.repository;

import com.example.vehicles.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Endpoint 1: GET /api/vehicles
    List<Vehicle> findByDeleted(String deleted);

    // Endpoint 2: GET /api/vehicles/low-stock-expensive
    @Query("""
            SELECT v
            FROM Vehicle v
            WHERE v.deleted = 'N'
              AND v.price > 20000
              AND v.stock < 10
            """)
    List<Vehicle> findLowStockAndExpensive();

    // Endpoint 3: PATCH /api/vehicles/delete/{model}
    Optional<Vehicle> findByModel(String model);
}
