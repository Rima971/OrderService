package com.swiggy.orderManager.exceptions;

import static com.swiggy.orderManager.constants.ErrorMessages.RESTAURANT_NOT_FOUND;

public class InvalidRestaurantId extends RuntimeException {
    public InvalidRestaurantId(){
        super(RESTAURANT_NOT_FOUND);
    }
}
