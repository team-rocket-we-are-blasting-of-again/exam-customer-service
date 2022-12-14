package com.teamrocket.customer.model.dto;

import com.teamrocket.customer.model.entity.CartEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CartDTO {
    private int customerId;
    private int restaurantId;
    private List<CartItemDTO> items = new ArrayList<>();
    private double totalPrice;
    private boolean withDelivery;

    public CartDTO(CartEntity cart) {
        this.customerId = cart.getCustomerId();
        this.restaurantId = cart.getRestaurantId();
        this.items = cart.getItems()
                .stream()
                .map(item -> CartItemDTO.builder()
                        .menuItemId(item.getMenuItemId())
                        .quantity(item.getQuantity())
                        .build())
                .toList();
        this.totalPrice = cart.getTotalPrice();
    }
}
