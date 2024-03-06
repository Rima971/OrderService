package com.swiggy.orderManager.entities;

import com.swiggy.orderManager.enums.OrderStatus;
import com.swiggy.orderManager.enums.OrderUpdateType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "updates")
@Data
public class Update {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Order order;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderUpdateType type;

    @Column(nullable = true)
    private String previousValue;

    @Column(nullable = true)
    private String updatedValue;

    @CreatedDate
    private LocalDateTime timestamp;

    public Update(Order order, OrderStatus status) {
        this.order = order;
        this.type = OrderUpdateType.STATUS;
        this.previousValue = order.getStatus().name();
        this.updatedValue = status.name();
    }

    private void updateStatus(OrderStatus status){
        this.order.setStatus(status);
    }
}
