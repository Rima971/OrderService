package com.swiggy.orderManager.services;

import com.swiggy.orderManager.adapters.AllocatorServiceAdapter;
import com.swiggy.orderManager.adapters.CatalogueServiceAdapter;
import com.swiggy.orderManager.dtos.ItemDto;
import com.swiggy.orderManager.dtos.MenuItemDto;
import com.swiggy.orderManager.dtos.MoneyDto;
import com.swiggy.orderManager.dtos.OrderRequestDto;
import com.swiggy.orderManager.entities.Order;
import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.repositories.OrderUpdatesDao;
import com.swiggy.orderManager.repositories.OrdersDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static com.swiggy.orderManager.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class OrdersServiceTest {
    @Mock
    private CatalogueServiceAdapter mockedCatalogueServiceAdapter;

    @Mock
    private AllocatorServiceAdapter mockedAllocatorServiceAdapter;

    @Mock
    private OrdersDao mockedOrdersDao;

    @Mock
    private OrderUpdatesDao mockedOrderUpdatesDao;

    @InjectMocks
    private OrdersService ordersService;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        Order.setCatalogueServiceAdapter(mockedCatalogueServiceAdapter);
        Order.setAllocatorServiceAdapter(mockedAllocatorServiceAdapter);
    }

    @AfterEach
    public void cleanUp(){
        Order.setCatalogueServiceAdapter(new CatalogueServiceAdapter());
        Order.setAllocatorServiceAdapter(new AllocatorServiceAdapter());
    }

    @Test
    public void test_shouldCreateAnOrderSuccessfullyAndCheckForValidItemsAndAllocateDeliveryAgent(){
        Order mockedOrder = mock(Order.class);
        MenuItemDto item = new MenuItemDto(TEST_MENU_ITEM_ID, TEST_RESTAURANT_ID, new MoneyDto(0, Currency.INR.name()));
        when(this.mockedOrdersDao.save(any(Order.class))).thenReturn(mockedOrder);
        when(this.mockedCatalogueServiceAdapter.fetchMenuItem(TEST_MENU_ITEM_ID, TEST_RESTAURANT_ID)).thenReturn(item);
        OrderRequestDto request = new OrderRequestDto(TEST_CUSTOMER_ID,TEST_RESTAURANT_ID, new ArrayList<>(List.of(new ItemDto(TEST_MENU_ITEM_ID, TEST_MENU_ITEM_QUANTITY))));

        assertDoesNotThrow(()-> {
            Order createdOrder = this.ordersService.create(request);
            assertEquals(mockedOrder, createdOrder);
        });
        verify(this.mockedOrdersDao, times(1)).save(any(Order.class));
        verify(this.mockedCatalogueServiceAdapter, times(1)).checkRestaurantExists(TEST_RESTAURANT_ID);
        verify(this.mockedCatalogueServiceAdapter, times(1)).fetchMenuItem(TEST_MENU_ITEM_ID, TEST_RESTAURANT_ID);
    }


}
