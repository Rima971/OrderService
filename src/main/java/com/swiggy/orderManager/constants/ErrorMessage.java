package com.swiggy.orderManager.constants;

import java.util.function.Function;

public class ErrorMessage {
    public static class GroupedIds {
        public final int itemId, restaurantId;

        public GroupedIds(int itemId, int restaurantId) {
            this.itemId = itemId;
            this.restaurantId = restaurantId;
        }
    }
    public static final Function<GroupedIds, String> ITEM_NOT_OF_GIVEN_RESTAURANT = (GroupedIds group) -> "Item with id "+group.itemId+" is does not belong to the given restaurant with id "+group.restaurantId+". Cannot process order.";
    public static final String ADDITION_CURRENCY_CONFLICT = "Adding money with different currency.";
    public static final String RESTAURANT_NOT_FOUND = "No restaurant exists with the given id";
    public static final String MENU_ITEM_NOT_FOUND = "No menu item exists with the given id";

}
