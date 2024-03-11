package com.swiggy.orderManager.exceptions;

import static com.swiggy.orderManager.constants.ErrorMessage.ADDITION_CURRENCY_CONFLICT;

public class AdditionBetweenDifferentCurrenciesException extends IllegalArgumentException {
    public AdditionBetweenDifferentCurrenciesException(){
        super(ADDITION_CURRENCY_CONFLICT);
    }
}
