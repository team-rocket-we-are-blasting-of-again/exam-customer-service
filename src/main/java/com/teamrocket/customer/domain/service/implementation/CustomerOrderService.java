package com.teamrocket.customer.domain.service.implementation;

import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.dto.CustomerOrderDTO;
import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import com.teamrocket.customer.domain.service.ICustomerOrderService;
import com.teamrocket.customer.infrastructure.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService implements ICustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public CustomerOrderEntity createCustomerOrder(CustomerEntity customer, NewCustomerOrder data) {
        // build customerdto from customer
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .addressId(customer.getAddressId())
                .phone(customer.getPhone())
                .customerOrderEntity(customer.getCustomerOrderEntity())
                .build();
        // find restaurant from restaurantid

        // find menuitemid for menuitemid
        int id = data.getItems().get(0).getMenuItemId();

        // build customerorderdto
        CustomerOrderEntity customerOrder = CustomerOrderDTO.builder()
                .menuItemId(id)
                .createdAt(data.getCreatedAt())
                .deliver(data.isWithDelivery())
                .deliveryPrice(420) // TODO: WHERE DO I GET THIS?
                .orderPrice(data.getTotalPrice())
                .restaurantName("NOT AVAILABLE") // TODO: INSERT RESTAURANT ID INSTEAD data.getRestaurantId()
                .restaurantAddress("NOT AVAILABLE")
                .phone(customer.getPhone()) // TODO: I DONT HAVE A RESTAURANT PHONE?!?!
                .customer(customerDTO)
                .systemOrderId(data.getId())
                .status(data.getStatus())
                .build();

        // save customer order
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrderEntity updateSystemOrder(OrderStatus orderStatus, int id) {
        return customerOrderRepository.setCustomerOrderStatus(orderStatus, id);
    }
}
