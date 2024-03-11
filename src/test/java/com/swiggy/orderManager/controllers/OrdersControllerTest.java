package com.swiggy.orderManager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.orderManager.adapters.CatalogueServiceAdapter;
import com.swiggy.orderManager.dtos.ItemDto;
import com.swiggy.orderManager.dtos.MenuItemDto;
import com.swiggy.orderManager.dtos.MoneyDto;
import com.swiggy.orderManager.dtos.OrderRequestDto;
import com.swiggy.orderManager.entities.Money;
import com.swiggy.orderManager.entities.Order;
import com.swiggy.orderManager.enums.Currency;
import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.services.OrdersService;
import org.aspectj.lang.annotation.After;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.swiggy.orderManager.TestConstants.*;
import static com.swiggy.orderManager.constants.SuccessMessage.ORDER_SUCCESSFULLY_CREATED;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdersControllerTest {
    private static final String BASE_URL = "/api/orders";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrdersService mockedOrdersService;

    @Mock
    private CatalogueServiceAdapter mockedCatalogueServiceAdapter;

    private Order testOrder;

    @BeforeEach
    public void setUp(){
        reset(this.mockedOrdersService);
        Order.setCatalogueServiceAdapter(this.mockedCatalogueServiceAdapter);
        when(this.mockedCatalogueServiceAdapter.fetchMenuItem(TEST_MENU_ITEM_ID, TEST_RESTAURANT_ID)).thenReturn(new MenuItemDto(TEST_MENU_ITEM_ID, TEST_RESTAURANT_ID, new MoneyDto(23, Currency.INR.name())));
        this.testOrder = Order.create(TEST_CUSTOMER_ID, TEST_RESTAURANT_ID, new ArrayList<>(List.of(new ItemDto(TEST_MENU_ITEM_ID, TEST_MENU_ITEM_QUANTITY))));
    }

    @AfterEach
    public void cleanUp(){
        Order.setCatalogueServiceAdapter(new CatalogueServiceAdapter());
    }
    @Test
    public void test_shouldCreateOrderSuccessfully() throws Exception {
        OrderRequestDto request = new OrderRequestDto(TEST_CUSTOMER_ID, TEST_RESTAURANT_ID, List.of(new ItemDto(TEST_MENU_ITEM_ID, TEST_MENU_ITEM_QUANTITY)));
        String mappedRequest = this.objectMapper.writeValueAsString(request);
        when(this.mockedOrdersService.create(any(OrderRequestDto.class))).thenReturn(this.testOrder);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mappedRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(ORDER_SUCCESSFULLY_CREATED))
                .andExpect(jsonPath("$.data.id").value(this.testOrder.getId()))
                .andExpect(jsonPath("$.data.customerId").value(this.testOrder.getCustomerId()))
                .andExpect(jsonPath("$.data.restaurantId").value(this.testOrder.getRestaurantId()))
                .andExpect(jsonPath("$.data.status").value(OrderStatus.CREATED.name()))
                .andExpect(jsonPath("$.data.netPrice.amount").value(this.testOrder.getNetPrice().getAmount()))
                .andExpect(jsonPath("$.data.netPrice.currency").value(this.testOrder.getNetPrice().getCurrency().name()))
                .andExpect(jsonPath("$.data.allocatedDeliveryAgentId").exists())
                .andExpect(jsonPath("$.data.timestamp").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.data.deliveryLocationPincode").exists())
                .andExpect(jsonPath("$.data.items.0").value(TEST_MENU_ITEM_QUANTITY));

        verify(this.mockedOrdersService, times(1)).create(any(OrderRequestDto.class));
    }

}
