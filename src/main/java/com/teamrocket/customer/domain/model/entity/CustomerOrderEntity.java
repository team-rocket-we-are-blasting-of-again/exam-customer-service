package com.teamrocket.customer.domain.model.entity;

import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.dto.OrderItem;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_order")
public class CustomerOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(targetEntity = OrderItemEntity.class,
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER)
    @JoinColumn(name = "coi_fk", referencedColumnName = "id")
    @Column(name = "order_items")
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @Column(name = "created_time", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @Column(name = "deliver", nullable = false)
    private boolean deliver;

    @Column(name = "delivery_price", nullable = false)
    private double deliveryPrice;

    @Column(name = "order_price", nullable = false)
    private double orderPrice;

    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "legacy_restaurant_id")
    private Integer legacyRestaurantId;

    @Column(name = "system_order_id", unique = true)
    private Integer systemOrderId;

    @Column(name = "legacy_system_order_id", unique = true)
    private Integer legacySystemOrderId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    CustomerEntity customer;

    public CustomerOrderEntity(NewCustomerOrder dto) {
        addOrderItemToOrderItemEntity(dto.getItems());
        this.createdAt = dto.getCreatedAt();
        this.deliver = dto.isWithDelivery();
        this.deliveryPrice = dto.getDeliveryPrice();
        this.orderPrice = dto.getTotalPrice();
        this.restaurantId = dto.getRestaurantId();
        this.systemOrderId = dto.getId();
        this.status = OrderStatus.PENDING;
    }

    private void addOrderItemToOrderItemEntity(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem ->
            this.orderItems.add(new OrderItemEntity(orderItem))
        );
    }

}
