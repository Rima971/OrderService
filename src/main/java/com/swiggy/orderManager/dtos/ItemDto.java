package com.swiggy.orderManager.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {
    @NotNull(message = "itemId is required")
    @Min(value = 0, message = "invalid itemId passed")
    private int itemId;

    @NotNull(message = "quantity is required")
    @Min(value = 0, message = "quantity cannot be negative")
    private int quantity;
}
