package com.swiggy.orderManager.exceptions;

import com.swiggy.orderManager.constants.ErrorMessages;

import static com.swiggy.orderManager.constants.ErrorMessages.ITEM_NOT_OF_GIVEN_RESTAURANT;

public class ItemRestaurantConflict extends RuntimeException {
    public ItemRestaurantConflict(int itemId, int restaurantId){
        super(ITEM_NOT_OF_GIVEN_RESTAURANT.apply(new ErrorMessages.GroupedIds(itemId, restaurantId)));
    }
}
