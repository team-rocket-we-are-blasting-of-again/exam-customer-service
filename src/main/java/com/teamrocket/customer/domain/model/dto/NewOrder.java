package com.teamrocket.customer.domain.model.dto;

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
    private List<OrderItem> items;
}
