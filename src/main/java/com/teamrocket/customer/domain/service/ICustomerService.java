package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    CustomerEntity registerCustomer(CustomerRegistrationRequest request);

    List<CustomerEntity> getCustomers();

    CustomerEntity getCustomerById(int id) throws ResourceNotFoundException;

    CustomerEntity updateCustomer(int id, CustomerEntity customerRequest) throws ResourceNotFoundException;

    Map<String, Boolean> deleteCustomer(int id) throws ResourceNotFoundException;
}
