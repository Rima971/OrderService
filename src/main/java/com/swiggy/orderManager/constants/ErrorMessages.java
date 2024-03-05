package com.swiggy.orderManager.constants;

import java.util.function.Consumer;
import java.util.function.Function;

public class ErrorMessages {
    public static final Function<Integer, String> ITEM_OUT_OF_STOCK = (Integer id) -> "Item with id "+id+" is out of stock at the moment. Cannot process order.";
}
