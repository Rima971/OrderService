package com.swiggy.orderManager.exceptions;

import com.swiggy.orderManager.constants.ErrorMessage;

import static com.swiggy.orderManager.constants.ErrorMessage.ITEM_NOT_OF_GIVEN_RESTAURANT;

public class ItemRestaurantConflictException extends RuntimeException {
    public ItemRestaurantConflictException(int itemId, int restaurantId){
        super(ITEM_NOT_OF_GIVEN_RESTAURANT.apply(new ErrorMessage.GroupedIds(itemId, restaurantId)));
    }
}
