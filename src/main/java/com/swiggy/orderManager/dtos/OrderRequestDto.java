package com.swiggy.orderManager.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "itemId is required")
    @Min(value = 0, message = "invalid itemId passed")
    private int customerId;

    @NotNull(message = "itemId is required")
    @Min(value = 0, message = "invalid itemId passed")
    private int restaurantId;

    @Valid
    @NotNull(message = "items is required")
    @NotEmpty(message = "items list cannot be empty")
    private List<ItemDto> items;
}
