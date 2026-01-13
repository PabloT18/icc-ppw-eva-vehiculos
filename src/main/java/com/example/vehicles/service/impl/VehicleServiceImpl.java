package com.example.vehicles.service.impl;

import com.example.vehicles.dto.OperationResponseDto;
import com.example.vehicles.dto.VehicleResponseDto;
import com.example.vehicles.dto.VehicleStockRequestDto;
import com.example.vehicles.entity.Vehicle;
import com.example.vehicles.mapper.VehicleMapper;
import com.example.vehicles.repository.VehicleRepository;
import com.example.vehicles.service.VehicleService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service implementation for managing vehicles.
 * 
 * Provides business logic operations for vehicle management including
 * retrieving active vehicles,
 * filtering vehicles by price and stock criteria, deleting vehicles, and
 * updating vehicle stock.
 * All database operations are managed within a transactional context.
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Retrieves all active (non-deleted) vehicles.
     * 
     * @return a list of {@link VehicleResponseDto} containing all vehicles marked
     *         as active
     */
    @Override
    public List<VehicleResponseDto> getAllActiveVehicles() {
        List<VehicleResponseDto> dtos = vehicleRepository.findByDeleted("N")
                .stream()
                .map(vehicle -> VehicleMapper.toResponseDto(vehicle))
                .toList();
        return dtos;
    }

    /**
     * Retrieves vehicles that have both high price and low stock.
     * 
     * Filters active vehicles to find those with a price greater than 20000
     * and stock less than 10.
     * 
     * @return a list of {@link VehicleResponseDto} matching the price and stock
     *         criteria
     */
    @Override
    public List<VehicleResponseDto> getLowStockExpensiveVehicles() {

        return vehicleRepository.findByDeleted("N")
                .stream()
                .filter(t -> t.getPrice() > 20000 && t.getStock() < 10)
                .map(VehicleMapper::toResponseDto)
                .toList();
    }

    /**
     * Soft deletes a vehicle by marking it as deleted.
     * 
     * Finds a vehicle by its model and marks it as deleted. If the vehicle is
     * already
     * deleted or does not exist, appropriate exceptions are thrown.
     * 
     * @param model the model of the vehicle to delete
     * @return an {@link OperationResponseDto} with a success message
     * @throws ResponseStatusException with NOT_FOUND status if vehicle does not
     *                                 exist
     * @throws ResponseStatusException with CONFLICT status if vehicle is already
     *                                 deleted
     */
    @Override
    public OperationResponseDto deleteByModel(String model) {
        Vehicle vehicle = vehicleRepository.findByModel(model)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        if ("S".equals(vehicle.getDeleted())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vehicle already deleted");
        }

        vehicle.setDeleted("N");
        vehicleRepository.save(vehicle);

        return new OperationResponseDto("Vehicle deleted successfully");
    }

    /**
     * Updates the stock quantity of a vehicle.
     * 
     * Finds a vehicle by its ID and updates its stock with the provided value.
     * 
     * @param request a {@link VehicleStockRequestDto} containing the vehicle ID and
     *                new stock value
     * @return a {@link VehicleResponseDto} representing the updated vehicle
     * @throws ResponseStatusException with NOT_FOUND status if vehicle does not
     *                                 exist
     */
    @Override
    public VehicleResponseDto updateStock(VehicleStockRequestDto request) {
        Vehicle vehicle = vehicleRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        vehicle.setStock(request.getStock());
        vehicleRepository.save(vehicle);

        return VehicleMapper.toResponseDto(vehicle);
    }
}
