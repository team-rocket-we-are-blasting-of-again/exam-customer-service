package com.teamrocket.customer.domain.service.implementation;


import com.teamrocket.CreateUserRequest;
import com.teamrocket.CreateUserResponse;
import com.teamrocket.Role;
import com.teamrocket.UserGrpc;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.infrastructure.repository.CustomerRepository;
import com.teamrocket.customer.domain.service.IRPCService;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RPCService implements IRPCService {
    @GrpcClient("grpc-service")
    private UserGrpc.UserBlockingStub unaryCall;

    private final CustomerRepository customerRepository;

    @Override
    public CreateUserResponse createCustomer(CustomerEntity customer, CustomerRegistrationRequest request) {

        CustomerEntity newCustomer = customerRepository.findByEmail(customer.getEmail());

        CreateUserRequest authService = CreateUserRequest.newBuilder()
                .setRole(Role.CUSTOMER)
                .setRoleId(newCustomer.getId())
                .setEmail(newCustomer.getEmail())
                .setPassword(request.password())
                .build();

        return unaryCall.createUser(authService);
    }
}
