package com.teamrocket.customer.service.implementation;

import com.teamrocket.customer.dto.NewCustomerEventDTO;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.repository.CustomerRepository;
import com.teamrocket.customer.service.ICustomerService;
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
    public Customer registerCustomer(CustomerRegistrationRequest request) {
        // TODO: Call location service before making a customer
        // Send address to location service and location will give me an address id
        // gRPC call to location service to get the address id
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .addressId(request.addressId())
                .phone(request.phone())
                .build();

        // TODO: check if email is valid
        // TODO: check if email is not taken
        Customer newCustomer = customerRepository.save(customer);

        NewCustomerEventDTO newCustomerEventDTO = new NewCustomerEventDTO(
                newCustomer.getFirstName(),
                newCustomer.getLastName(),
                newCustomer.getEmail(),
                newCustomer.getAddressId(),
                newCustomer.getPhone()
        );

        rpcService.createCustomer(customer, request);

        kafkaService.publishEvent(newCustomerEventDTO);

        return newCustomer;
    }

    @Override
    public List<Customer> getCustomers() {
        // TODO: outcomment below and remove other code below that
//        return new ArrayList<>(customerRepository.findAll());
        List<Customer> customerList = new ArrayList<>();
        customerRepository.findAll()
                .forEach(customerList::add);
        return customerList;
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer does not exist with id: " + id)
                );
    }

    @Override
    public Customer updateCustomer(int id, Customer customerRequest) {
        Customer customer = customerRepository.findById(id)
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
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Can not delete customer with id: " + id)
                );

        customerRepository.delete(customer);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }

}
