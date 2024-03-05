package com.swiggy.orderManager.exceptions;

import static com.swiggy.orderManager.constants.ErrorMessages.ITEM_OUT_OF_STOCK;

public class ItemOutOfStock extends RuntimeException {
    public ItemOutOfStock(int itemId){
        super(ITEM_OUT_OF_STOCK.apply(itemId));
    }
}
