package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.exceptions.AdditionBetweenDifferentCurrenciesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    public void test_shouldAddMoneyOfSameCurrency(){
        Money firstMoney = new Money(2, Currency.INR);
        Money secondMoney = new Money(10, Currency.INR);

        assertDoesNotThrow(()->firstMoney.add(secondMoney));
        assertEquals(firstMoney, new Money(12, Currency.INR));
    }

    @Test
    public void test_shouldThrowAdditionBetweenDifferentCurrenciesExceptionWhenAddingMoneyOfDifferentCurrencies(){
        Money firstMoney = new Money(2, Currency.INR);
        Money secondMoney = new Money(10, Currency.USD);

        assertThrows(AdditionBetweenDifferentCurrenciesException.class, ()->firstMoney.add(secondMoney));
    }
}
