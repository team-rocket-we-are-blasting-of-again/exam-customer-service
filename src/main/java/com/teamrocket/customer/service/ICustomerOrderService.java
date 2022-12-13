package com.teamrocket.customer.service;

import com.teamrocket.customer.model.dto.CartDTO;
import com.teamrocket.customer.model.dto.CustomerDTO;
import com.teamrocket.customer.model.dto.NewCustomerOrder;
import com.teamrocket.customer.model.entity.CartEntity;
import com.teamrocket.customer.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.model.enums.OrderStatus;

import java.util.Map;
import java.util.Optional;

public interface ICustomerOrderService {
    CustomerOrderEntity createCustomerOrder(CustomerDTO customer, NewCustomerOrder data);

    int updateSystemOrder(OrderStatus orderStatus, int id);

    CartDTO addItemToCart(String customerId, CartEntity cartEntity);

    String purchaseOrder(String customerId);

    Optional<CustomerOrderEntity> findCustomerOrderBySystemOrderId(int systemOrderId);

    Map<String, Boolean> emptyCart(int customerId);
}
