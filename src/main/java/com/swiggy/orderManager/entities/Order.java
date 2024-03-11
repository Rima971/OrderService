package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.adapters.CatalogueServiceAdapter;
import com.swiggy.orderManager.dtos.ItemDto;
import com.swiggy.orderManager.dtos.MenuItemDto;
import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.exceptions.InvalidRestaurantId;
import com.swiggy.orderManager.exceptions.ItemRestaurantConflict;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, updatable = false)
    private int customerId;

    @Column(nullable = false)
    private int deliveryLocationPincode;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "orderedItems", joinColumns = @JoinColumn(name = "orderItemsId"))
    @MapKeyColumn(name = "itemId")
    @Column(name = "itemQuantity")
    private Map<Integer, Integer> items; // id -> quantity

    @Column(nullable = false)
    private int restaurantId;

    @Enumerated(EnumType.STRING)
    @Setter
    private OrderStatus status;

    @Column(nullable = true)
    private int allocatedDeliveryAgentId;

    @CreatedDate
    private LocalDateTime timestamp;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "netPriceValue")),
            @AttributeOverride(name = "currency", column = @Column(name = "netPriceCurrency"))
    })
    private Money netPrice = null;

    private Order(int customerId, List<ItemDto> items) throws ItemRestaurantConflict,InvalidRestaurantId, IOException {
        this.items = new HashMap<>();
        items.forEach(item->this.items.put(item.getItemId(), item.getQuantity()));
        this.customerId = customerId;
        checkItemsBelongToGivenRestaurantAndCalculateNetPrice();
        obtainDeliveryLocation();
        allocateDeliverer();

        this.status = OrderStatus.CREATED;
    }

    private void checkItemsBelongToGivenRestaurantAndCalculateNetPrice() throws ItemRestaurantConflict, InvalidRestaurantId, IOException {
        CatalogueServiceAdapter catalogueService = new CatalogueServiceAdapter();
        if (!catalogueService.restaurantExists(this.restaurantId)) {
            throw new InvalidRestaurantId();
        }
        for (Map.Entry<Integer,Integer> entry : this.items.entrySet()) {
            try{
                MenuItemDto item = catalogueService.fetchMenuItem(entry.getKey(), this.restaurantId);
                if (this.netPrice == null){
                    this.netPrice = item.getPrice();
                } else {
                    this.netPrice.add(item.getPrice());
                }
            } catch (Exception e){
                // catch specific exception: resource not found
                throw new ItemRestaurantConflict(entry.getKey(), this.restaurantId);
            }
        }
    }

    private void obtainDeliveryLocation(){
        // fetch customer
        // get their location
    }


    private void allocateDeliverer(){
        new Thread(()->{
            /*
             * allocation logic
             * */
            this.status = OrderStatus.ASSIGNED;
        });
    }

    public static Order create(int customerId, List<ItemDto> items) throws ItemRestaurantConflict, InvalidRestaurantId, IOException {
        return new Order(customerId, items);
    }
}
