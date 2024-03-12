package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.adapters.AllocatorServiceAdapter;
import com.swiggy.orderManager.adapters.CatalogueServiceAdapter;
import com.swiggy.orderManager.dtos.ItemDto;
import com.swiggy.orderManager.dtos.MenuItemDto;
import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.exceptions.InvalidRestaurantIdException;
import com.swiggy.orderManager.exceptions.ItemRestaurantConflictException;
import com.swiggy.orderManager.exceptions.NoItemOrderedException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private static CatalogueServiceAdapter CATALOGUE_SERVICE_ADAPTER = new CatalogueServiceAdapter();
    private static AllocatorServiceAdapter ALLOCATOR_SERVICE_ADAPTER = new AllocatorServiceAdapter();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, updatable = false)
    private int customerId;

    @Column(nullable = false)
    private int deliveryLocationPincode;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "orderedItems", joinColumns = @JoinColumn(name = "orderId"))
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

    private Order(int customerId, int restaurantId, List<ItemDto> items) throws ItemRestaurantConflictException, InvalidRestaurantIdException {
        if (items.isEmpty()) throw new NoItemOrderedException();
        this.items = new HashMap<>();
        items.forEach(item->this.items.put(item.getItemId(), item.getQuantity()));
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        checkItemsBelongToGivenRestaurantAndCalculateNetPrice();
        obtainDeliveryLocation();
        this.status = OrderStatus.CREATED;

        allocateDeliverer();
    }

    private void checkItemsBelongToGivenRestaurantAndCalculateNetPrice() throws ItemRestaurantConflictException, InvalidRestaurantIdException {
        CATALOGUE_SERVICE_ADAPTER.checkRestaurantExists(this.restaurantId);
        for (Map.Entry<Integer,Integer> entry : this.items.entrySet()) {
                MenuItemDto item = CATALOGUE_SERVICE_ADAPTER.fetchMenuItem(entry.getKey(), this.restaurantId);
                System.out.println("item: "+item);
                if (this.netPrice == null){
                    this.netPrice = item.getPrice();
                } else {
                    this.netPrice.add(item.getPrice());
                }
        }
    }

    private void obtainDeliveryLocation(){
        // fetch customer
        // get their location
    }


    private void allocateDeliverer(){
        new Thread(()->{
            this.allocatedDeliveryAgentId = ALLOCATOR_SERVICE_ADAPTER.allocate().getId();
            this.status = OrderStatus.ASSIGNED;
        }).start();
    }

    public static Order create(int customerId, int restaurantId, List<ItemDto> items) throws ItemRestaurantConflictException, InvalidRestaurantIdException {
        return new Order(customerId, restaurantId, items);
    }

    public static void setCatalogueServiceAdapter(CatalogueServiceAdapter adapter){
        CATALOGUE_SERVICE_ADAPTER = adapter;
    }
}
