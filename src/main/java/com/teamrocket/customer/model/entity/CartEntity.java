package com.teamrocket.customer.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "cart")
public class CartEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    // Does not need auto generation since I set customer id on a cart entity before it is saved to a customer
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
}
