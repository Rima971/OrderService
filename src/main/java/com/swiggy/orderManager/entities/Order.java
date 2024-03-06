package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.exceptions.ItemDoesNotBelongToGivenRestaurant;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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

    @NotNull
    private int deliveryLocationPincode;

    @NotNull
    private Map<Integer, Integer> items; // id -> quantity

    @NotNull
    private int restaurantId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Nullable
    private int allocatedDeliveryAgentId;

    @CreatedDate
    private LocalDateTime timestamp;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "netPriceValue")),
            @AttributeOverride(name = "currency", column = @Column(name = "netPriceCurrency"))
    })
    private Money netPrice;

    public Order(int customerId, Map<Integer, Integer> items) throws ItemDoesNotBelongToGivenRestaurant {
        this.customerId = customerId;
        this.items = items;
        checkItemsBelongToGivenRestaurantAndCalculateNetPrice();
        obtainDeliveryLocation();

        this.status = OrderStatus.CREATED;
    }

    private void checkItemsBelongToGivenRestaurantAndCalculateNetPrice() throws ItemDoesNotBelongToGivenRestaurant {

    }

    public void obtainDeliveryLocation(){

    }


    public void allocateDeliverer(){}
}
