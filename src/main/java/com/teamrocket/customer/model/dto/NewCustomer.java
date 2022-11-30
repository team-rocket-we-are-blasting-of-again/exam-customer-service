package com.teamrocket.customer.model.dto;

public record NewCustomer(
        String firstName,
        String lastName,
        String email,
        int addressId,
        String phone
) {
}