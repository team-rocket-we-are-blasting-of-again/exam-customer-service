package com.teamrocket.customer.domain.service.implementation;

import com.google.protobuf.Descriptors;
import com.teamrocket.proto.*;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.infrastructure.repository.CustomerRepository;
import com.teamrocket.customer.domain.service.IAuthClient;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthClient implements IAuthClient {
    @GrpcClient("grpc-service")
    private UserGrpc.UserBlockingStub synchronousCall;

    private final CustomerRepository customerRepository;


    @Override
    public Map<Descriptors.FieldDescriptor, Object> createCustomer(CustomerEntity customer, CustomerRegistrationRequest request) {

        CustomerEntity newCustomer = customerRepository.findByEmail(customer.getEmail());

        log.info("New customer registration was successfully looked up in rpc service with unique email: {}",
                newCustomer.getEmail());

        // Creating the request object
        CreateUserRequest authServiceRequest = CreateUserRequest.newBuilder()
                .setRole(Role.CUSTOMER)
                .setRoleId(newCustomer.getId())
                .setEmail(newCustomer.getEmail())
                .setPassword(request.password())
                .build();

        // Getting the response
        CreateUserResponse authServiceResponse = synchronousCall.createUser(authServiceRequest);

        // Process the response and send it back to the caller (auth service)
        return authServiceResponse.getAllFields();
    }
}
