package com.teamrocket.customer.domain.model;

import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString()
public class RestaurantCustomerRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private int addressId;
    private CustomerEntity customer;
}
