package com.teamrocket.customer.application;

import com.teamrocket.proto.CustomerServiceGrpc;
import com.teamrocket.proto.DeliveryDataResponse;
import com.teamrocket.proto.SystemOrderIdRequest;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.infrastructure.repository.CustomerOrderRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@GrpcService
public class CustomerCourierServerService extends CustomerServiceGrpc.CustomerServiceImplBase {

    @Autowired
    CustomerOrderRepository customerOrderRepository;

    @Override
    public void getDeliveryData(SystemOrderIdRequest request, StreamObserver<DeliveryDataResponse> responseObserver) {
        int systemOrderId = request.getSystemOrderId();

        log.info("Courier service has initialized a gRPC request for a customer order with system id: " + systemOrderId);

        try {
            Optional<CustomerOrderEntity> customerOrderEntity = customerOrderRepository.findCustomerOrderEntityBySystemOrderId(systemOrderId);
            String customerFullName = null;

            customerFullName = customerOrderEntity.get().getCustomer().getFirstName() + " " + customerOrderEntity.get().getCustomer().getLastName();

            DeliveryDataResponse response = DeliveryDataResponse.newBuilder()
                    .setCustomerName(customerFullName)
                    .setCustomerPhone(customerOrderEntity.get().getCustomer().getPhone())
                    .setDropOfAddressId(customerOrderEntity.get().getCustomer().getAddressId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("Customer information was successfully fetched with gRPC from courier service with system order id: "
                    + systemOrderId + " and response body: " + response);

        } catch (NoSuchElementException e) {
            log.error("No customer has a valid order with the provided system order id: " + systemOrderId);
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }
}
