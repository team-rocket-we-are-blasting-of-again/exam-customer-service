package com.teamrocket.customer.domain.service.implementation;


import com.google.protobuf.Descriptors;
import com.teamrocket.CreateUserRequest;
import com.teamrocket.CreateUserResponse;
import com.teamrocket.Role;
import com.teamrocket.UserGrpc;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.domain.model.entity.CartEntity;
import com.teamrocket.customer.infrastructure.repository.CustomerRepository;
import com.teamrocket.customer.domain.service.IRPCService;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class RPCService implements IRPCService {
    @GrpcClient("grpc-service")
    private UserGrpc.UserBlockingStub synchronousCall;

    private final CustomerRepository customerRepository;


    @Override
    public Map<Descriptors.FieldDescriptor, Object> createCustomer(CustomerEntity customer, CustomerRegistrationRequest request) {

        CustomerEntity newCustomer = customerRepository.findByEmail(customer.getEmail());

        log.info("New customer registration was successfully looked up in rpc service with unique email: {}",
                newCustomer.getEmail());

        CreateUserRequest authServiceRequest = CreateUserRequest.newBuilder()
                .setRole(Role.CUSTOMER)
                .setRoleId(newCustomer.getId())
                .setEmail(newCustomer.getEmail())
                .setPassword(request.password())
                .build();

        CreateUserResponse authServiceResponse = synchronousCall.createUser(authServiceRequest);

        return authServiceResponse.getAllFields();
    }

    private Map<Descriptors.FieldDescriptor, Object> restaurantCalculateTotalPrice(CartEntity entityl) {
        // todo: talk to magda about proto schema
        return null;
    }
}
