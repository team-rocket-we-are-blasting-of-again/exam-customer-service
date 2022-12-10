package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.entity.CartEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;

import java.util.Map;
import java.util.Optional;

public interface ICustomerOrderService {
    CustomerOrderEntity createCustomerOrder(CustomerDTO customer, NewCustomerOrder data);

    int updateSystemOrder(OrderStatus orderStatus, int id);

    CartEntity addItemToCart(String customerId, CartEntity cartEntity);

    String purchaseOrder(String customerId);

    Optional<CustomerOrderEntity> findCustomerOrderBySystemOrderId(int systemOrderId);

    Map<String, Boolean> emptyCart(int customerId);
}
