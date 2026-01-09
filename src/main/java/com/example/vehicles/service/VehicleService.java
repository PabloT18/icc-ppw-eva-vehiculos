package com.example.vehicles.service;

import com.example.vehicles.dto.OperationResponseDto;
import com.example.vehicles.dto.VehicleResponseDto;
import com.example.vehicles.dto.VehicleStockRequestDto;

import java.util.List;

public interface VehicleService {

    List<VehicleResponseDto> getAllActiveVehicles();

    List<VehicleResponseDto> getLowStockExpensiveVehicles();

    OperationResponseDto deleteByModel(String model);

    VehicleResponseDto updateStock(VehicleStockRequestDto request);
}
