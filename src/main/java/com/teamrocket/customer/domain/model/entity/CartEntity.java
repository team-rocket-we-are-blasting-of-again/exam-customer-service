package com.teamrocket.customer.domain.model.entity;

import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.dto.OrderItem;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@ToString
@Table(name = "cart")
public class CartEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
    @Column(name = "customer_id", unique = true, nullable = false)
    private int customerId;
    @Column(name = "restaurant_id")
    private int restaurantId;
    @OneToMany(targetEntity = CartItemEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "ci_fk", referencedColumnName = "customer_id")
    private List<CartItemEntity> items = new ArrayList<>();
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "delivery")
    private boolean withDelivery;

// TODO: !?#"!?#"!??!?!
//    public CartEntity(CartEntity dto) {
//        addOrderItemToOrderItemEntity(dto.getItems());
//        this.createdAt = dto.getCreatedAt();
//        this.deliver = dto.isWithDelivery();
//        this.deliveryPrice = dto.getDeliveryPrice();
//        this.orderPrice = dto.getTotalPrice();
//        this.restaurantId = dto.getRestaurantId();
//        this.status = OrderStatus.PENDING;
//    }
//
//    private void addOrderItemToOrderItemEntity(List<CartItemEntity> orderItems) {
//        orderItems.forEach(orderItem ->
//                this.orderItems.add(new CartItemEntity(orderItem))
//        );
//    }
}
