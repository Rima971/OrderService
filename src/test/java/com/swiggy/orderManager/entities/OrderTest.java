package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.adapters.AllocatorServiceAdapter;
import com.swiggy.orderManager.adapters.CatalogueServiceAdapter;
import com.swiggy.orderManager.dtos.ItemDto;
import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.exceptions.NoItemOrderedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.swiggy.orderManager.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class OrderTest {
    @Mock
    private CatalogueServiceAdapter mockedCatalogueServiceAdapter;

    @Mock
    private AllocatorServiceAdapter mockedAllocatorServiceAdapter;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        Order.setCatalogueServiceAdapter(this.mockedCatalogueServiceAdapter);
        Order.setAllocatorServiceAdapter(this.mockedAllocatorServiceAdapter);
    }

    @AfterEach
    public void cleanUp(){
        Order.setCatalogueServiceAdapter(new CatalogueServiceAdapter());
        Order.setAllocatorServiceAdapter(new AllocatorServiceAdapter());
    }

    @Test
    public void test_shouldCreateOrderWithCreatedStatus(){
        Order order = assertDoesNotThrow(()->Order.create(TEST_CUSTOMER_ID, TEST_RESTAURANT_ID, new ArrayList<>(List.of(new ItemDto(TEST_MENU_ITEM_ID, TEST_MENU_ITEM_QUANTITY)))));
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }
    @Test
    public void test_shouldThrowNoItemOrderedExceptionIfItemListIsEmpty(){
        assertThrows(NoItemOrderedException.class, ()->Order.create(TEST_CUSTOMER_ID, TEST_RESTAURANT_ID, new ArrayList<>()));
    }

    @Test
    public void test_shouldCreateACorrectMapOfItemIdToItsQuantityForAGivenListOfItems(){

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
