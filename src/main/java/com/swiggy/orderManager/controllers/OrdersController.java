package com.swiggy.orderManager.controllers;

import com.swiggy.orderManager.dtos.GenericHttpResponse;
import com.swiggy.orderManager.dtos.OrderRequestDto;
import com.swiggy.orderManager.entities.Order;
import com.swiggy.orderManager.exceptions.InexistentMenuItemException;
import com.swiggy.orderManager.exceptions.ItemRestaurantConflictException;
import com.swiggy.orderManager.services.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.swiggy.orderManager.constants.SuccessMessage.ORDER_SUCCESSFULLY_CREATED;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @PostMapping
    public ResponseEntity<GenericHttpResponse> create(@Valid @RequestBody OrderRequestDto orderRequestDto) throws InexistentMenuItemException, ItemRestaurantConflictException {
        Order createdOrder = this.ordersService.create(orderRequestDto);
        return GenericHttpResponse.create(HttpStatus.CREATED, ORDER_SUCCESSFULLY_CREATED, createdOrder);
    }
}
