package com.swiggy.orderManager.repositories;

import com.swiggy.orderManager.entities.Order;
import com.swiggy.orderManager.entities.Update;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderUpdatesDao extends JpaRepository<Update, Integer> {
}
