package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.exceptions.NoItemOrderedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.swiggy.orderManager.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    @Test
    public void test_shouldThrowNoItemOrderedExceptionIfItemListIsEmpty(){
        assertThrows(NoItemOrderedException.class, ()->Order.create(TEST_CUSTOMER_ID, TEST_RESTAURANT_ID, new ArrayList<>()));
    }
    @Test
    public void test_shouldThrowItemRestaurantConflictIfAnyOfTheItemPassedDoesNotBelongToTheGivenRestaurant(){

    }

    @Test
    public void test_shouldInitiallyHaveCreatedStatus(){

    }

    @Test
    public void test_shouldUpdateStatusToAssignedWhenAgentIsAllocated(){

    }

}
