package com.swiggy.orderManager.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.orderManager.dtos.GenericHttpResponse;
import com.swiggy.orderManager.dtos.MenuItemDto;
import com.swiggy.orderManager.exceptions.InexistentMenuItemException;
import com.swiggy.orderManager.exceptions.InvalidRestaurantIdException;
import com.swiggy.orderManager.exceptions.ItemRestaurantConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

import static com.swiggy.orderManager.constants.ErrorMessage.*;

@Component
public class CatalogueServiceAdapter {
    @Autowired
    private ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String RESTAURANTS_RESOURCE_URL = "http://localhost:8080/api/restaurants/";
    private static final Function<Integer, String> MENU_ITEMS_RESOURCE_URL = (Integer restaurantId) -> "http://localhost:8080/api/restaurants/"+restaurantId+"/menu-items/";

    public void checkRestaurantExists(int restaurantId) {
        URI url = URI.create(RESTAURANTS_RESOURCE_URL+restaurantId);
        try{
            this.restTemplate.getForObject(url, GenericHttpResponse.class);
        } catch (HttpClientErrorException e){
            if (e.getStatusCode() == HttpStatus.CONFLICT){
                throw new InvalidRestaurantIdException();
            }
            throw e;
        }
    }

    public MenuItemDto fetchMenuItem(int itemId, int restaurantId) throws InexistentMenuItemException, ItemRestaurantConflictException {
        URI url = URI.create(MENU_ITEMS_RESOURCE_URL.apply(restaurantId)+itemId);
        try{
            GenericHttpResponse response = this.restTemplate.getForObject(url, GenericHttpResponse.class);
            assert response != null;
            return this.objectMapper.convertValue(response.getData(), MenuItemDto.class);
        } catch (HttpClientErrorException e){
            if (e.getStatusCode() == HttpStatus.CONFLICT){
                if (Objects.equals(e.getMessage(), MENU_ITEM_NOT_FOUND)){
                    throw new InexistentMenuItemException();
                } else if (Objects.equals(e.getMessage(), ITEM_NOT_OF_GIVEN_RESTAURANT.apply(new GroupedIds(itemId, restaurantId)))){
                    throw new ItemRestaurantConflictException(itemId, restaurantId);
                }
                throw e;
            }
        }
        throw new InexistentMenuItemException();
    }
}
