package com.swiggy.orderManager.dtos;

import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.exceptions.UnsupportedCurrency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Arrays;

@Data
public class MoneyDto {
    @NotNull(message = "money amount is required")
    @Min(value = 0, message = "money amount should be at least 0")
    double amount;

    @NotNull(message = "money currency is required")
    Currency currency;

    public MoneyDto(double amount, String currency) throws UnsupportedCurrency {
        if (!Arrays.stream(Currency.values()).anyMatch(c-> c.name().equals(currency))) throw new UnsupportedCurrency();
        this.amount = amount;
        this.currency = Currency.valueOf(currency);
    }
}
