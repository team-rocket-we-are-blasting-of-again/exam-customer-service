package com.teamrocket.customer.model.dto;

import com.teamrocket.customer.model.entity.CartEntity;
import com.teamrocket.customer.model.entity.CartItemEntity;
import com.teamrocket.customer.model.entity.OrderItemEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewOrder {
    private int customerId;
    private int restaurantId;
    private Date createdAt;
    private boolean withDelivery;
    private List<OrderItemEntity> items = new ArrayList<>();

    public NewOrder(CartEntity cart) {
        this.customerId = cart.getCustomerId();
        this.restaurantId = cart.getRestaurantId();
        this.createdAt = new Date();
        this.withDelivery = cart.isWithDelivery();
        mapCartItemToOrder(cart.getItems());
    }

    private void mapCartItemToOrder(List<CartItemEntity> cartItemsList) {
        cartItemsList.forEach(cartItemEntity -> this.items.add(new OrderItemEntity(cartItemEntity)));
    }
}
