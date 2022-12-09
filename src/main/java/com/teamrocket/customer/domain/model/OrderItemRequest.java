package com.teamrocket.customer.domain.model;

import com.teamrocket.customer.domain.model.entity.CartItemEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemRequest {
    private int menuItemId = 1;
    private int quantity = 2;
    private double price = 3;

    public OrderItemRequest(int menuItemId, int quantity) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }
}
