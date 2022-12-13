package com.teamrocket.customer.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {
    private int menuItemId;
    private int quantity;
}
