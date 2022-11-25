package com.teamrocket.customer.service;

import com.teamrocket.AuthServiceGrpc;
import com.teamrocket.NewCustomer;
import com.teamrocket.VerifiedUser;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.repository.CustomerRepository;
import io.grpc.stub.annotations.GrpcGenerated;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    @GrpcClient("grpc-service")
    private AuthServiceGrpc.AuthServiceBlockingStub unaryCall;

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public ResponseEntity<Customer> registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // TODO: check if email is valid
        // TODO: check if email is not taken
        // TODO: gRPC call
        Customer newCustomer = customerRepository.save(customer);

        NewCustomer authService = NewCustomer.newBuilder()
                .setEmail(customer.getEmail())
                .setPassword(request.password())
                .setRoleId(newCustomer.getId())
                .build();

        VerifiedUser unaryCallCustomer = unaryCall.createCustomer(authService);

        // TODO: EMIT CUSTOMER EVENT when customer has been verified

        return ResponseEntity.ok(newCustomer);
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with id: " + id));
        return ResponseEntity.ok(customer);
    }

    @Override
    public ResponseEntity<Customer> updateCustomer(int id, Customer customerRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with id: " + id));

        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());

        Customer updatedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with id: " + id));


        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
