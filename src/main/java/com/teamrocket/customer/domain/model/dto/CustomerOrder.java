package com.teamrocket.customer.domain.model.dto;

import com.teamrocket.customer.domain.model.entity.OrderItemEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerOrder {
    private OrderItemEntity menuItemEntity; // TODO: MAKE THIS TO MENUITEMID INSTEAD
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    private boolean deliver;
    private double deliveryPrice;
    private double orderPrice;
    private int restaurantId;
    private int systemOrderId;
    private OrderStatus status; // TODO: might have to change this to string
}
