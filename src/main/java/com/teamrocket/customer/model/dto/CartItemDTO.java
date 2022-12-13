package com.teamrocket.customer.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CartItemDTO {
    private int menuItemId;
    private int quantity;
}
