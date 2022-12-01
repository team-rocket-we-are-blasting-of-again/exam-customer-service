package com.teamrocket.customer.domain.service;


import com.teamrocket.CreateUserResponse;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;

public interface IRPCService {
    CreateUserResponse createCustomer(CustomerEntity customer, CustomerRegistrationRequest request);
}
