package com.teamrocket.customer.service.implementation;


import com.teamrocket.CreateUserRequest;
import com.teamrocket.CreateUserResponse;
import com.teamrocket.Role;
import com.teamrocket.UserGrpc;
import com.teamrocket.customer.exceptions.RegistrationFailException;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.repository.CustomerRepository;
import com.teamrocket.customer.service.IRPCService;
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
    public CreateUserResponse createCustomer(Customer customer, CustomerRegistrationRequest request) {

        Customer newCustomer = customerRepository.findByEmail(customer.getEmail());

        CreateUserRequest authService = CreateUserRequest.newBuilder()
                .setRole(Role.CUSTOMER)
                .setRoleId(newCustomer.getId())
                .setEmail(customer.getEmail())
                .setPassword(request.password())
                .build();

        CreateUserResponse createUserRequest = unaryCall.createUser(authService);
        if (createUserRequest == null) {
            // gRPC StatusRuntimeException
            throw new RegistrationFailException("Registration failed on user: " + customer.getEmail());
        }

        return createUserRequest;
    }

}
