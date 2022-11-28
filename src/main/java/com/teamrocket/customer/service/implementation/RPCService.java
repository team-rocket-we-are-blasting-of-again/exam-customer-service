package com.teamrocket.customer.service.implementation;

import com.teamrocket.AuthServiceGrpc;
import com.teamrocket.NewCustomer;
import com.teamrocket.VerifiedUser;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
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
    private AuthServiceGrpc.AuthServiceBlockingStub unaryCall;

    private final CustomerRepository customerRepository;

    public VerifiedUser verifyCustomer(Customer customer, CustomerRegistrationRequest request) {
        Customer newCustomer = customerRepository.findByEmail(customer.getEmail());

        NewCustomer authService = NewCustomer.newBuilder()
                .setEmail(customer.getEmail())
                .setPassword(request.password())
                .setRoleId(newCustomer.getId())
                .build();

        // TODO: Need verification from Auth service
        return unaryCall.verifyCustomer(authService);
    }

}
