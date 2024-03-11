package com.swiggy.orderManager.dtos;

import com.swiggy.orderManager.entities.Money;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItemDto {
    @NotNull(message = "id is required")
    @Min(value = 0, message = "invalid id passed")
    private int id;

    @NotNull(message = "restaurantId is required")
    @Min(value = 0, message = "invalid itemId passed")
    private int restaurantId;

    @Column(nullable = false)
    @Valid
    private MoneyDto price;

    public Money getPrice(){
        return new Money(this.price.getAmount(), this.price.getCurrency());
    }
}
