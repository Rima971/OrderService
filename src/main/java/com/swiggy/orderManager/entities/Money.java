package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.exceptions.AdditionBetweenDifferentCurrenciesException;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class Money {
    private double amount;
    private Currency currency;
    public void add(Money money){
        if (money.currency != this.currency) throw new AdditionBetweenDifferentCurrenciesException();
        this.amount += money.amount;
    }
}
