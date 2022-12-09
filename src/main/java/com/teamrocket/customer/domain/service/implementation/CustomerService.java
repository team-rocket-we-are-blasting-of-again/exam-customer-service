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
import org.springframework.beans.factory.annotation.Autowired;
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
        customer.setEmail(customerRequest.getEmail());
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

        // TODO: IMPLEMEN LOGGING HERE AS WELL
        // TODO: Implement all cases
        switch (kafkaTopic) {
            case NEW_ORDER_PLACED:
                subject = "MTOGO: New order has been placed";
                messageBody = "Thank you for your order. We will begin the process of validating your " +
                        "order and will keep you updated throughout the whole process.";
                break;
            default:
                // TODO: real error handling implementation
                throw new RuntimeException("BLABLABLA");
        }

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customer.getEmail())
                .subject(subject)
                .message("Dear " + customer.getFirstName() + " " + customer.getLastName()
                        + ",\n" + messageBody)
                .build();

        kafkaService.customerNotificationEvent(customerNotification);
    }

}
