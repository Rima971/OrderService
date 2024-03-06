package com.swiggy.orderManager.entities;

import lombok.Data;

@Data
public class Customer {
    private int id;
    private int userId;
    private int deliveryLocationPincode;
}
