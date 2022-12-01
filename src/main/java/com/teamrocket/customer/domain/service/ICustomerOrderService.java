package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;

public interface ICustomerOrderService {
    CustomerOrderEntity createCustomerOrder(CustomerEntity customer, NewCustomerOrder data);

    CustomerOrderEntity updateSystemOrder(OrderStatus orderStatus, int id);
}
