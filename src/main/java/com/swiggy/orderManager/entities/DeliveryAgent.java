package com.swiggy.orderManager.entities;

import lombok.Data;

@Data
public class DeliveryAgent {
    private int id;
    private int userId;
    private int currentLocationPincode;
}
