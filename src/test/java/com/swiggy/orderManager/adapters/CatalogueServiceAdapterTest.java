package com.swiggy.orderManager.adapters;

import com.swiggy.orderManager.dtos.MenuItemDto;
import com.swiggy.orderManager.entities.Money;
import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.exceptions.InexistentMenuItemException;
import com.swiggy.orderManager.exceptions.InvalidRestaurantIdException;
import com.swiggy.orderManager.exceptions.ItemRestaurantConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static com.swiggy.orderManager.TestConstants.TEST_RESTAURANT_ID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CatalogueServiceAdapterTest {
    public static final int TEST_ID = 1;
    public static final int TEST_PRICE_AMOUNT = 0;
    public static final Currency TEST_PRICE_CURRENCY = Currency.INR;
    @Autowired
    private CatalogueServiceAdapter catalogueServiceAdapter;
    @Test
    public void test_shouldNotThrowAnyExceptionIfGivenRestaurantIdExists(){
        assertDoesNotThrow(()->this.catalogueServiceAdapter.checkRestaurantExists(TEST_ID));
    }

    @Test
    public void test_shouldThrowInvalidRestaurantIdExceptionIfGivenRestaurantIdDoesNotExist(){
        assertThrows(InvalidRestaurantIdException.class, ()->this.catalogueServiceAdapter.checkRestaurantExists(0));
    }

    @Test
    public void test_shouldFetchMenuItemOfARestaurant(){
            MenuItemDto menuItem = assertDoesNotThrow(()->this.catalogueServiceAdapter.fetchMenuItem(TEST_ID, TEST_ID));
            assertNotNull(menuItem);
            assertEquals(TEST_ID, menuItem.getId());
            assertEquals(TEST_ID, menuItem.getRestaurantId());
            assertEquals(new Money(0, Currency.INR), menuItem.getPrice());
    }

    @Test void test_shouldThrowInexistentMenuItemExceptionWhenPassingInvalidItemId(){
        assertThrows(InexistentMenuItemException.class, ()->this.catalogueServiceAdapter.fetchMenuItem(0, TEST_ID));
    }

    @Test void test_ShouldThrowItemRestaurantConflictExceptionIfItemDoesNotBelongToTheGivenRestaurantOrRestaurantIdIsInvalid(){
        assertThrows(ItemRestaurantConflictException.class, ()->this.catalogueServiceAdapter.fetchMenuItem(TEST_ID, 0));
    }
}
