package com.teamrocket.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewOrder {
    private int restaurantId;
    private Date createdAt;
    private boolean withDelivery;
    private List<OrderItem> items;
}
