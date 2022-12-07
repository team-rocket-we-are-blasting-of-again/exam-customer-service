package com.teamrocket.customer.domain.model.dto;

import com.teamrocket.customer.domain.model.enums.OrderStatus;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerOrderDTO {
    private int menuItemId;
    private Date createdAt;
    private boolean deliver;
    private double deliveryPrice;
    private double orderPrice;
    private String restaurantName;
    private String restaurantAddress;
    private String phone;
    private CustomerDTO customer;
    private int systemOrderId;
    private OrderStatus status; // TODO: might have to change this to string
}
