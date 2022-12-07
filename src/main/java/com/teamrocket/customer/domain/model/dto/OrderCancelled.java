package com.teamrocket.customer.domain.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderCancelled {
    private int systemOrderId;
    private String reason;
}
