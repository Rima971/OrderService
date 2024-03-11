package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.exceptions.AdditionBetweenDifferentCurrencies;
import com.swiggy.orderManager.exceptions.UnsupportedCurrency;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@Data
@Embeddable
@AllArgsConstructor
public class Money {
    private double amount;
    private Currency currency;
    public void add(Money money){
        if (money.currency != this.currency) throw new AdditionBetweenDifferentCurrencies();
        this.amount += money.amount;
    }
}
