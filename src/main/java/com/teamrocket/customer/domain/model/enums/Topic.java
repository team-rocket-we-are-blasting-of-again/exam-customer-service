package com.teamrocket.customer.domain.model.enums;

import lombok.ToString;

@ToString
public enum Topic {
    CUSTOMER_NOTIFICATION,
    NEW_CUSTOMER,
    NEW_ORDER_PLACED,
    ORDER_ACCEPTED,
    ORDER_READY,
    ORDER_CANCELED,
    ORDER_PICKED_UP,
    ORDER_DELIVERED,
    ORDER_CLAIMED
}
