package com.teamrocket.customer.service;

import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // TODO: check if email is valid
        // TODO: check if email is not taken
        customerRepository.save(customer);
    }
}
