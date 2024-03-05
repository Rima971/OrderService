package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.Currency;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class Money {
    double amount;
    Currency currency;
}
