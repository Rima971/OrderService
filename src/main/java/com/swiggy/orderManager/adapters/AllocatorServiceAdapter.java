package com.swiggy.orderManager.adapters;

import com.swiggy.orderManager.dtos.DeliveryAgentDto;
import org.springframework.stereotype.Component;

@Component
public class AllocatorServiceAdapter {
    public DeliveryAgentDto allocate(){
        return new DeliveryAgentDto();
    }
}
