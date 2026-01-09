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

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<VehicleResponseDto> getAllActiveVehicles() {
        return vehicleRepository.findByDeleted("N")
                .stream()
                .map(VehicleMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VehicleResponseDto> getLowStockExpensiveVehicles() {
        return vehicleRepository.findLowStockAndExpensive()
                .stream()
                .map(VehicleMapper::toResponseDto)
                .toList();
    }

    @Override
    public OperationResponseDto deleteByModel(String model) {
        Vehicle vehicle = vehicleRepository.findByModel(model)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        if ("S".equals(vehicle.getDeleted())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vehicle already deleted");
        }

        vehicle.setDeleted("S");
        vehicleRepository.save(vehicle);

        return new OperationResponseDto("Vehicle deleted successfully");
    }

    @Override
    public VehicleResponseDto updateStock(VehicleStockRequestDto request) {
        Vehicle vehicle = vehicleRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        vehicle.setStock(request.getStock());
        vehicleRepository.save(vehicle);

        return VehicleMapper.toResponseDto(vehicle);
    }
}
