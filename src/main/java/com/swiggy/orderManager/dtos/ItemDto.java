package com.swiggy.orderManager.dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
public class ItemDto {
    private int itemId;
    private int quantity;
}
