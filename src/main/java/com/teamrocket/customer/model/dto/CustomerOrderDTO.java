package com.teamrocket.customer.model.dto;

import com.teamrocket.customer.model.enums.OrderStatus;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerOrderDTO {
    private Date createdAt;
    private boolean deliver;
    private double deliveryPrice;
    private double orderPrice;
    private int restaurantId;
    private int systemOrderId;
    private OrderStatus status;
}
