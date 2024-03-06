package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.exceptions.AdditionBetweenDifferentCurrencies;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class Money {
    double amount;
    Currency currency;

    public void add(Money money){
        if (money.currency != this.currency) throw new AdditionBetweenDifferentCurrencies();
        this.amount += money.amount;
    }
}
