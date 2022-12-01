package com.teamrocket.customer.domain.model;


public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        int addressId,
        String phone,
        String password
) {
}
