package com.example.vehicles.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VehicleStockRequestDto {

    @NotNull
    private Long id;

    @NotNull
    @Min(0)
    private Integer stock;

    public VehicleStockRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
