package com.example.vehicles.repository;

import com.example.vehicles.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Vehicle entity persistence operations.
 * 
 * Provides database access and query methods for Vehicle objects using Spring
 * Data JPA.
 * Extends JpaRepository to inherit standard CRUD operations.
 * 
 * @author
 * @version 1.0
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

  /**
   * Retrieves a list of vehicles filtered by their deletion status.
   * 
   * @param deleted the deletion status filter (typically "true" or "false")
   * @return a List of Vehicle objects matching the specified deletion status,
   *         or an empty list if no vehicles are found
   */
  List<Vehicle> findByDeleted(String deleted);

  /**
   * Finds a single vehicle by its model name.
   * 
   * @param model the vehicle model name to search for
   * @return an Optional containing the Vehicle if found, or an empty Optional if
   *         not found
   */
  Optional<Vehicle> findByModel(String model);
}
