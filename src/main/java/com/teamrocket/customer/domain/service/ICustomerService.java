package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.enums.Topic;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    CustomerDTO registerCustomer(CustomerRegistrationRequest request);

    List<CustomerDTO> getCustomers();

    CustomerDTO getCustomerById(int id) throws ResourceNotFoundException;

    CustomerDTO updateCustomer(int id, CustomerEntity customerRequest) throws ResourceNotFoundException;

    Map<String, Boolean> deleteCustomer(int id);

    void notifyCustomer(CustomerDTO customer, Topic kafkaTopic);
}
