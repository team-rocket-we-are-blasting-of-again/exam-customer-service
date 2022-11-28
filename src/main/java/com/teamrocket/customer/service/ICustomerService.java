package com.teamrocket.customer.service;

import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    Customer registerCustomer(CustomerRegistrationRequest request);

    List<Customer> getCustomers();

    Customer getCustomerById(int id) throws ResourceNotFoundException;

    Customer updateCustomer(int id, Customer customerRequest) throws ResourceNotFoundException;

    Map<String, Boolean> deleteCustomer(int id) throws ResourceNotFoundException;
}
