package com.swiggy.orderManager.services;

import com.swiggy.orderManager.dtos.OrderRequestDto;
import com.swiggy.orderManager.entities.Order;
import com.swiggy.orderManager.entities.Update;
import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.exceptions.InexistentMenuItemException;
import com.swiggy.orderManager.exceptions.ItemRestaurantConflictException;
import com.swiggy.orderManager.repositories.OrderUpdatesDao;
import com.swiggy.orderManager.repositories.OrdersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private OrderUpdatesDao orderUpdatesDao;

    public Order create(OrderRequestDto dto) throws InexistentMenuItemException, ItemRestaurantConflictException {
        Order order = Order.create(dto.getCustomerId(), dto.getRestaurantId(), dto.getItems());
        Order savedOrder = this.ordersDao.save(order);
        new Thread(()->{
            savedOrder.allocateDeliverer();
            Update update = new Update(savedOrder, OrderStatus.ASSIGNED);
            this.orderUpdatesDao.save(update);
        }).start();
        return savedOrder;
    }
}
