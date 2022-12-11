package com.teamrocket.customer.model.dto;

import com.teamrocket.customer.model.enums.OrderStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewCustomerOrder {
    private int id;
    private int restaurantId;
    private int customerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    private OrderStatus status; // TODO: might have to change this to string
    private boolean withDelivery;
    private double deliveryPrice;
    private double totalPrice;
    private List<OrderItem> items;
}

