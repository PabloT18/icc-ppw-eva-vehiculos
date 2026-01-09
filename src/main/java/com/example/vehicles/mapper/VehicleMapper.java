package com.example.vehicles.mapper;

import com.example.vehicles.dto.VehicleResponseDto;
import com.example.vehicles.entity.Vehicle;

public class VehicleMapper {

    private VehicleMapper() {
        // Evita instanciación
    }

    public static VehicleResponseDto toResponseDto(Vehicle vehicle) {
        return new VehicleResponseDto(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getPrice(),
                vehicle.getStock());
    }
}
