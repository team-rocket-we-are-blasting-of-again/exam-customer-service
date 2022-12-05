package com.teamrocket.customer.domain.model.entity;

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
@Builder
@Entity
@Table(name = "cart")
public class CartEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "customer_id", unique = true, nullable = false)
    private int customerId;
    @Column(name = "restaurant_id")
    private int restaurantId;
    @OneToMany(targetEntity = CartItemEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
//            orphanRemoval = true
    )
    @JoinColumn(name = "ci_fk", referencedColumnName = "id")
    private List<CartItemEntity> items = new ArrayList<>();
    @Column(name = "total_price")
    private int totalPrice;
    @Column(name = "delivery")
    private boolean withDelivery;
}
