package com.teamrocket.customer.model;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        int addressId,
        String phone,
        String password
) {
}
