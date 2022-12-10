package com.teamrocket.customer.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCustomer {
    private String firstName;
    private String lastName;
    private String email;
    private int addressId;
    private String phone;
}