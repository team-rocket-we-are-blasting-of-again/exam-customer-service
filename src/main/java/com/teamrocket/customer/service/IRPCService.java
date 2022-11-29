package com.teamrocket.customer.service;


import com.teamrocket.CreateUserResponse;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;

public interface IRPCService {

    CreateUserResponse createCustomer(Customer customer, CustomerRegistrationRequest request);

}
