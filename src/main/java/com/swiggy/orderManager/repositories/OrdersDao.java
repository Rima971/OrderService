package com.swiggy.orderManager.repositories;

import com.swiggy.orderManager.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDao extends JpaRepository<Order, Integer> {
}
