
package com.example.vehicles.dto;

public class OperationResponseDto {

    private String message;

    public OperationResponseDto() {
    }

    public OperationResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
