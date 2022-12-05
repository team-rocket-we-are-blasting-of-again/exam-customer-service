package com.teamrocket.customer.domain.model.dto;

import com.teamrocket.customer.domain.model.entity.CartEntity;
import com.teamrocket.customer.domain.model.entity.CartItemEntity;
import com.teamrocket.customer.domain.model.entity.OrderItemEntity;
import lombok.*;

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
    private List<OrderItemEntity> items;

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
