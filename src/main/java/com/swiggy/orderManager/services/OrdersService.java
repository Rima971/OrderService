package com.swiggy.orderManager.services;

import com.swiggy.orderManager.dtos.OrderRequestDto;
import com.swiggy.orderManager.entities.Order;
import com.swiggy.orderManager.repositories.OrdersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    public Order create(OrderRequestDto dto) throws IOException {
        Order order = Order.create(dto.getCustomerId(), dto.getRestaurantId(), dto.getItems());
        return this.ordersDao.save(order);
    }
}
