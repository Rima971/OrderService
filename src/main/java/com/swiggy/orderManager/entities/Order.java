package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.exceptions.ItemOutOfStock;
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
    int id;

    @Column(nullable = false, updatable = false)
    int customerId;

    @NotNull
    int deliveryLocationPincode;

    @NotNull
    Map<Integer, Integer> items; // id -> quantity

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Nullable
    int allocatedDeliveryAgentId;

    @CreatedDate
    private LocalDateTime timestamp;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "netPriceValue")),
            @AttributeOverride(name = "currency", column = @Column(name = "netPriceCurrency"))
    })
    Money netPrice;

    public Order(int customerId, Map<Integer, Integer> items) throws ItemOutOfStock {
        this.customerId = customerId;
        this.items = items;
        checkItemsAreInStockAndCalculateNetPrice();
        obtainDeliveryLocation();

        this.status = OrderStatus.CREATED;
    }

    private void checkItemsAreInStockAndCalculateNetPrice() throws ItemOutOfStock {

    }

    public void obtainDeliveryLocation(){

    }


    public void allocateDeliverer(){}
}
