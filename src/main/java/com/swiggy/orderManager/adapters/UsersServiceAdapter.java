package com.swiggy.orderManager.adapters;

import com.swiggy.orderManager.dtos.CustomerDto;
import com.swiggy.orderManager.dtos.UserApiResponseDto;
import com.swiggy.orderManager.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UsersServiceAdapter {
    public UserApiResponseDto authorize(String username, String password, List<UserRole> roles){
        return new UserApiResponseDto();
    }

    public CustomerDto fetchCustomer(int customerId){
        return new CustomerDto();
    }
}
