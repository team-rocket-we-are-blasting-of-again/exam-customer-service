package com.teamrocket.customer.domain.service.implementation;

import com.google.protobuf.Descriptors;
import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.dto.CustomerNotification;
import com.teamrocket.customer.domain.model.enums.Topic;
import com.teamrocket.customer.domain.service.ICustomerService;
import com.teamrocket.customer.domain.model.dto.NewCustomer;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.infrastructure.repository.CustomerRepository;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerService implements ICustomerService {
    private final AuthClient authClient;
    private final CustomerRepository customerRepository;
    private final KafkaService kafkaService;


    @Override
    @Transactional
    public CustomerDTO registerCustomer(CustomerRegistrationRequest request) {
        // TODO: Not implemented but the plan was to have a location service
        // call that location service before making a customer
        // Send address to location service and location will give me an address id
        // gRPC call to location service to get the address id
        CustomerEntity customer = CustomerEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .addressId(request.addressId())
                .phone(request.phone())
                .build();

        log.info("New customer entity was successfully made in customer service with unique email: {}",
                customer.getEmail());

        CustomerEntity newCustomer = customerRepository.save(customer);
        log.info("New customer registration was successfully saved in customer service with unique email: {}",
                newCustomer.getEmail());

        Map<Descriptors.FieldDescriptor, Object> authedCustomer = authClient.createCustomer(customer, request);
        log.info("New customer was successfully authed from auth service with request: {}",
                authedCustomer);

        NewCustomer newCustomerEvent = new NewCustomer(
                newCustomer.getFirstName(),
                newCustomer.getLastName(),
                newCustomer.getEmail(),
                newCustomer.getAddressId(),
                newCustomer.getPhone()
        );

        log.info("New customer registration event dto was successfully made in customer service with unique email: {}",
                newCustomerEvent.getEmail());

        kafkaService.newCustomerEvent(newCustomerEvent);

        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName(newCustomer.getFirstName())
                .lastName(newCustomer.getLastName())
                .email(newCustomer.getEmail())
                .addressId(newCustomer.getAddressId())
                .phone(newCustomer.getPhone())
                .build();

        log.info("New customer dto was successfully made in customer service with unique email: {}",
                customerDTO.getEmail());

        return customerDTO;
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        for (CustomerEntity customer : customerRepository.findAll()) {
            customers.add(new CustomerDTO(customer));
        }

        log.info("All customers where successfully fetched and added to customer dto list in customer service with a size of: {}",
                customers.size());

        return customers;
    }

    @Override
    public CustomerDTO getCustomerById(int id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer does not exist with id: " + id)
                );

        log.info("customers was successfully fetched with id {} and added to customer dto in customer service with unique email: {}",
                customer.getId(),
                customer.getEmail());

        return new CustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(int id, CustomerEntity customerRequest) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Can not find and update customer with id: " + id)
                );

        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());

        // updating customer email is not allowed since it needs to be
        // validated from auth service.
        // since auth service did not implement this in time the validation check will not take place.
        // customer.setEmail(customerRequest.getEmail());

        customer.setAddressId(customerRequest.getAddressId());
        customer.setPhone(customerRequest.getPhone());

        log.info("customers was successfully fetched with id {} and updated in customer service with unique email: {}",
                customer.getId(),
                customer.getEmail());

        CustomerEntity customerEntity = customerRepository.save(customer);

        log.info("customers was successfully saved in customer service with unique email: {}",
                customer.getEmail());

        return new CustomerDTO(customerEntity);
    }

    @Override
    public Map<String, Boolean> deleteCustomer(int id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Can not delete customer with id: " + id)
                );

        customerRepository.delete(customer);

        log.info("customers was successfully deleted with id {} and unique email {} from customer service",
                customer.getId(),
                customer.getEmail());

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public void notifyCustomer(CustomerDTO customer, Topic kafkaTopic) {
        String subject = "";
        String messageBody = "";

        log.info("Customer used to notify notification service has the following properties: {}", customer.toString());

        switch (kafkaTopic) {
            case NEW_ORDER_PLACED:
                subject = "MTOGO: New order has been placed";
                messageBody = "Thank you for your order. \nWe will begin the process of validating your " +
                        "order and will keep you updated throughout the whole process." +
                        "\nKind regards, " +
                        "\n\nMTOGO A/S";

                log.info("switch case in customer service was hit with topic: {}", kafkaTopic);
                break;
            case ORDER_ACCEPTED:
                subject = "MTOGO: Order has been accepted";
                messageBody = "Your order has been accepted and preparation has started at " + customer.getCustomerOrderList().get(0).getCreatedAt() +
                        "\nWe will inform you when the order is ready." +
                        "\nKind regards, " +
                        "\n\nMTOGO A/S";

                log.info("switch case in customer service was hit with topic: {}", kafkaTopic);
                break;
            case ORDER_READY:
                subject = "MTOGO: Order is ready for pickup";
                messageBody = "The restaurant has now finished your order and the food is ready to be enjoyed." +
                        "\nKind regards, " +
                        "\n\nMTOGO A/S";

                log.info("switch case in customer service was hit with topic: {}", kafkaTopic);
                break;
            case ORDER_CANCELED:
                subject = "MTOGO: Order has been cancelled";
                messageBody = "Your order has been cancelled. \nThe reason for this is as follows: " + customer.getReason() + "." +
                        "If you did not cancel your order please try again or feel free to contact our customer support for further information or questions you might have. " +
                        "\nKind regards, " +
                        "\n\nMTOGO A/S";

                log.info("switch case in customer service was hit with topic: {}", kafkaTopic);
                break;
            case ORDER_PICKED_UP:
                subject = "MTOGO: Order has been picked up";
                messageBody = "Your order has been picked up by our courier and will be with you shortly." +
                        "\nYour order consists of: " + customer.getCustomerOrderList() +
                        "\nKind regards, " +
                        "\n\nMTOGO A/S";

                log.info("switch case in customer service was hit with topic: {}", kafkaTopic);
                break;
            case ORDER_DELIVERED:
                subject = "MTOGO: Order has been delivered";
                messageBody = "Your order has been delivered by our courier." +
                        "\nThank you for your order and we hope our service was of high quality and met your expectations." +
                        " If you have any comments please do not hesitate to let us know. We hope to hear from you soon again." +
                        "\n\nYour receipt will look as follows: \n" +
                        "Time your food was prepared: " + customer.getCustomerOrderList().get(0).getCreatedAt() +
                        "\nDelivery price: " + customer.getCustomerOrderList().get(0).getDeliveryPrice() +
                        "\nWith a total order price of: " + customer.getCustomerOrderList().get(0).getOrderPrice() +
                        "\nKind regards, " +
                        "\n\nMTOGO A/S";

                log.info("switch case in customer service was hit with topic: {}", kafkaTopic);
                break;
            case ORDER_CLAIMED:
                subject = "MTOGO: Order has been claimed";
                messageBody = "You order has been claimed by one of our couriers. " +
                        "\nWe will inform your when the courier has picked up your order." +
                        "\nKind regards, " +
                        "\n\nMTOGO A/S";

                log.info("switch case in customer service was hit with topic: {}", kafkaTopic);
                break;
            default:
                log.warn("Switch case in customer service received an unknown kafka topic: {}", kafkaTopic);
                throw new RuntimeException("An error occurred in customer service when listening on kafka topic: {}" + kafkaTopic);
        }

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customer.getEmail())
                .subject(subject)
                .message("Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n" + messageBody)
                .build();

        log.info("Kafka event for notification service was build with the following body: {}", customerNotification);

        kafkaService.customerNotificationEvent(customerNotification);
    }

}
