package com.swiggy.orderManager.dtos;

import lombok.Data;

@Data
public class MenuItemApiResponseDto {
    private int id;
    private RestaurantApiResponseDto restaurant;
    private MoneyDto price;
}
