package com.teamrocket.customer.domain.model.dto;

import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.entity.OrderItemEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private int addressId;
    private String phone;
    private List<CustomerOrderDTO> customerOrderList;

    public CustomerDTO(CustomerEntity customer) {
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.addressId = customer.getAddressId();
        this.phone = customer.getPhone();
//        this.customerOrderList = customer.getCustomerOrder();
        this.customerOrderList = customer.getCustomerOrder()
                .stream()
                .map(order -> CustomerOrderDTO.builder()
                        .createdAt(order.getCreatedAt())
                        .deliver(order.isDeliver())
                        .deliveryPrice(order.getDeliveryPrice())
                        .restaurantId(order.getRestaurantId())
                        .systemOrderId(order.getSystemOrderId())
                        .status(order.getStatus())
                        .build()).toList();
    }
}
