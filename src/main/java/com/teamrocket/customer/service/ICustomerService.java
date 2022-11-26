package com.teamrocket.customer.service;

import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    ResponseEntity<Customer> registerCustomer(CustomerRegistrationRequest request);

    //ResponseEntity<List<Customer>> getCustomers();
    List<Customer> getCustomers();

    ResponseEntity<Customer> getCustomerById(int id) throws ResourceNotFoundException;

    ResponseEntity<Customer> updateCustomer(int id, Customer customerRequest) throws ResourceNotFoundException;

    ResponseEntity<Map<String, Boolean>> deleteCustomer(int id) throws ResourceNotFoundException;
}
