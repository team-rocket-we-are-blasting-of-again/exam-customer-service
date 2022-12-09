package com.teamrocket.customer.domain.model.dto;

import com.teamrocket.customer.domain.model.entity.OrderItemEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private OrderStatus status; // TODO: might have to change this to string
}
