package com.teamrocket.customer.model.entity;

import com.teamrocket.customer.model.dto.NewCustomerOrder;
import com.teamrocket.customer.model.dto.OrderItem;
import com.teamrocket.customer.model.enums.OrderStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_order")
public class CustomerOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(targetEntity = OrderItemEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
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
    @Column(name = "restaurant_id", nullable = false)
    private int restaurantId;
    @Column(name = "system_order_id", nullable = false, unique = true)
    private int systemOrderId;
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

    public CustomerOrderEntity(CartEntity cart) {
        this.restaurantId = cart.getCustomerId();
        this.restaurantId = cart.getRestaurantId();
        addCartItemsToOrderItems(cart.getItems());
        this.orderPrice = cart.getTotalPrice();
        this.deliver = cart.isWithDelivery();
    }

    private void addCartItemsToOrderItems(List<CartItemEntity> cartItems) {
        cartItems.forEach(cartItem ->
                this.orderItems.add(new OrderItemEntity(cartItem))
        );
    }
}
