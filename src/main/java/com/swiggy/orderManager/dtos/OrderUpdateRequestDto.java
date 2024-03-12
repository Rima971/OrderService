package com.swiggy.orderManager.dtos;

import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.exceptions.UnsupportedOrderStatusException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Optional;

@Data
@AllArgsConstructor
public class OrderUpdateRequestDto {
    private Optional<OrderStatus> status;

    public void setStatus(Optional<String> status) throws UnsupportedOrderStatusException {
        if (status.isEmpty()) return;
        try{
            this.status = Optional.of(OrderStatus.valueOf(status.get()));
        } catch (IllegalArgumentException e){
            throw new UnsupportedOrderStatusException();
        }
    }
}
