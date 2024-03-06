package com.swiggy.orderManager.exceptions;

import static com.swiggy.orderManager.constants.ErrorMessages.ADDITION_CURRENCY_CONFLICT;

public class AdditionBetweenDifferentCurrencies extends IllegalArgumentException {
    public AdditionBetweenDifferentCurrencies(){
        super(ADDITION_CURRENCY_CONFLICT);
    }
}
