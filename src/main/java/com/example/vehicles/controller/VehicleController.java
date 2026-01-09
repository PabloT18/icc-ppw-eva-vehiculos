package com.example.vehicles.controller;

import com.example.vehicles.dto.OperationResponseDto;
import com.example.vehicles.dto.VehicleResponseDto;
import com.example.vehicles.dto.VehicleStockRequestDto;
import com.example.vehicles.service.VehicleService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // ENDPOINT 1
    // GET /api/vehicles
    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllActiveVehicles());
    }

    // ENDPOINT 2
    // GET /api/vehicles/low-stock-expensive
    @GetMapping("/low-stock-expensive")
    public ResponseEntity<List<VehicleResponseDto>> getLowStockExpensive() {
        return ResponseEntity.ok(vehicleService.getLowStockExpensiveVehicles());
    }

    // ENDPOINT 3
    // PATCH /api/vehicles/delete/{model}
    @PatchMapping("/delete/{model}")
    public ResponseEntity<OperationResponseDto> deleteByModel(
            @PathVariable String model) {

        return ResponseEntity.ok(vehicleService.deleteByModel(model));
    }

    // ENDPOINT 4
    // PATCH /api/vehicles/stock
    @PatchMapping("/stock")
    public ResponseEntity<VehicleResponseDto> updateStock(
            @Valid @RequestBody VehicleStockRequestDto request) {

        return ResponseEntity.ok(vehicleService.updateStock(request));
    }
}
