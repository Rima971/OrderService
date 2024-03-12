package com.swiggy.orderManager.dtos;

import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.exceptions.UnsupportedCurrencyException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Arrays;

@Data
public class MoneyDto {
    @NotNull(message = "money amount is required")
    @Min(value = 0, message = "money amount should be at least 0")
    double amount;

    @NotBlank(message = "money currency is required")
    Currency currency;

    public MoneyDto(double amount, String currency) throws UnsupportedCurrencyException {
        this.amount = amount;
        try{
            this.currency = Currency.valueOf(currency);
        } catch (IllegalArgumentException e){
            throw new UnsupportedCurrencyException();
        }
    }

    public void setCurrency(String currency) throws UnsupportedCurrencyException {
        try {
            this.currency = Currency.valueOf(currency);
        } catch (IllegalArgumentException e){
            throw new UnsupportedCurrencyException();
        }
    }
}
