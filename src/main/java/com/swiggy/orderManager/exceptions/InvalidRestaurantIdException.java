package com.swiggy.orderManager.exceptions;

import static com.swiggy.orderManager.constants.ErrorMessage.RESTAURANT_NOT_FOUND;

public class InvalidRestaurantIdException extends RuntimeException {
    public InvalidRestaurantIdException(){
        super(RESTAURANT_NOT_FOUND);
    }
}
