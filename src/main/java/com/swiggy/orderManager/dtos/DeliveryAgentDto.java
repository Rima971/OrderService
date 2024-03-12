package com.swiggy.orderManager.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAgentDto {
    @NotNull(message = "id is required")
    @Min(value = 0, message = "invalid id passed")
    private int id;

    @NotNull(message = "userId is required")
    @Min(value = 0, message = "invalid userId passed")
    private int userId;

    @NotNull(message = "pincode is required")
    @Min(value = 100000, message = "pincode must be at least 100000")
    private int currentLocationPincode;
}
