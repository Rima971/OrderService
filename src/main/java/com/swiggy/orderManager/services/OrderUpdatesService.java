package com.swiggy.orderManager.services;

import com.swiggy.orderManager.dtos.OrderUpdateRequestDto;
import com.swiggy.orderManager.entities.Order;
import com.swiggy.orderManager.entities.Update;
import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.enums.OrderUpdateType;
import com.swiggy.orderManager.exceptions.OrderNotFoundException;
import com.swiggy.orderManager.exceptions.StatusMissingException;
import com.swiggy.orderManager.exceptions.UnsupportedOrderStatusException;
import com.swiggy.orderManager.repositories.OrderUpdatesDao;
import com.swiggy.orderManager.repositories.OrdersDao;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderUpdatesService {
    @Autowired
    private OrderUpdatesDao orderUpdatesDao;

    @Autowired
    private OrdersDao ordersDao;

    public Update create(int orderId, OrderUpdateType type, OrderUpdateRequestDto dto) throws StatusMissingException, UnsupportedOrderStatusException {
        Order order = this.ordersDao.findById(orderId).orElseThrow(OrderNotFoundException::new);
        switch (type){
            case STATUS -> {
                if (dto.getStatus().isEmpty()) throw new StatusMissingException();
                return new Update(order, dto.getStatus().get());
            }
        }
        throw new UnsupportedOrderStatusException();
    }
}
