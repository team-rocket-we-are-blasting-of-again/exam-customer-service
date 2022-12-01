package com.teamrocket.customer.domain.model.dto;

public record NewCustomer(
        String firstName,
        String lastName,
        String email,
        int addressId,
        String phone
) {
}