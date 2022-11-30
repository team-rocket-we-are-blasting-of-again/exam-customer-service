package com.teamrocket.customer.dto;

public record NewCustomerEventDTO(
        String firstName,
        String lastName,
        String email,
        int addressId,
        String phone
) {
}