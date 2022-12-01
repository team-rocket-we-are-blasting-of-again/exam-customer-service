package com.teamrocket.customer.domain.service.implementation;

import com.teamrocket.customer.domain.service.ICustomerService;
import com.teamrocket.customer.domain.model.dto.NewCustomer;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.infrastructure.repository.CustomerRepository;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {
    private final RPCService rpcService;
    private final CustomerRepository customerRepository;
    private final KafkaService kafkaService;

    public CustomerService(RPCService rpcService, CustomerRepository customerRepository, KafkaService kafkaService) {
        this.rpcService = rpcService;
        this.customerRepository = customerRepository;
        this.kafkaService = kafkaService;
    }

    @Override
    @Transactional
    public CustomerEntity registerCustomer(CustomerRegistrationRequest request) {
        // TODO: Call location service before making a customer
        // Send address to location service and location will give me an address id
        // gRPC call to location service to get the address id
        CustomerEntity customer = CustomerEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .addressId(request.addressId())
                .phone(request.phone())
                .build();

        // TODO: check if email is valid

        CustomerEntity newCustomer = customerRepository.save(customer);

        // TODO: OUTCOMMENT whent going into production to call Auth service
        rpcService.createCustomer(customer, request);

        NewCustomer newCustomerEvent = new NewCustomer(
                newCustomer.getFirstName(),
                newCustomer.getLastName(),
                newCustomer.getEmail(),
                newCustomer.getAddressId(),
                newCustomer.getPhone()
        );

        kafkaService.newCustomerEvent(newCustomerEvent);

        return newCustomer;
    }

    @Override
    public List<CustomerEntity> getCustomers() {
        return new ArrayList<>(customerRepository.findAll());
    }

    @Override
    public CustomerEntity getCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer does not exist with id: " + id)
                );
    }

    @Override
    public CustomerEntity updateCustomer(int id, CustomerEntity customerRequest) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Can not find and update customer with id: " + id)
                );

        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setAddressId(customerRequest.getAddressId());
        customer.setPhone(customerRequest.getPhone());

        return customerRepository.save(customer);
    }

    @Override
    public Map<String, Boolean> deleteCustomer(int id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Can not delete customer with id: " + id)
                );

        customerRepository.delete(customer);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }

}
