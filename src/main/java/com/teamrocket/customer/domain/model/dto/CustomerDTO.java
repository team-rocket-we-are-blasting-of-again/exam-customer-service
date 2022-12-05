package com.teamrocket.customer.domain.model.dto;

import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import lombok.*;

import java.util.List;

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
    private List<CustomerOrderEntity> customerOrderEntity;

    public CustomerDTO(CustomerEntity customer) {
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.addressId = customer.getAddressId();
        this.phone = customer.getPhone();
    }
}
