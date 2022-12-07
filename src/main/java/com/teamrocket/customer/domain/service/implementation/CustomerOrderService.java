package com.teamrocket.customer.domain.service.implementation;

import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.dto.NewOrder;
import com.teamrocket.customer.domain.model.entity.CartEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import com.teamrocket.customer.domain.service.ICustomerOrderService;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.infrastructure.repository.CartRepository;
import com.teamrocket.customer.infrastructure.repository.CustomerOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerOrderService implements ICustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;

    private final CartRepository cartRepository;

    private final RPCService rpcService;

    @Autowired
    private CamundaService camundaService;

    @Override
    public CustomerOrderEntity createCustomerOrder(CustomerDTO customer, NewCustomerOrder data) {
        // TODO: missing implementation for delivery pricing for now its 19kr :)
        data.setDeliveryPrice(19.0);
//        // build customerdto from customer
//        CustomerEntity customerEntity = CustomerEntity.builder()
//                .firstName(customer.getFirstName())
//                .lastName(customer.getLastName())
//                .email(customer.getEmail())
//                .addressId(customer.getAddressId())
//                .phone(customer.getPhone())
//                .customerOrderEntity(customer.getCustomerOrderEntity())
//                .build();
//
//        // TODO: RELATIONS, OrderItemEntity, customerid i customerordentity,
//        //  customer email i customerorderentity, status ser også forkert ud i customerorderentitydb
//        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
//                .menuItemId(data.getItems().get(0).getMenuItemId())
//                .quantity(data.getItems().get(0).getQuantity())
//                .build();
        // build customerorderdto
//        CustomerOrderEntity customerOrder = CustomerOrderEntity.builder()
//                .orderItems(orderItemEntity)
//                .createdAt(data.getCreatedAt())
//                .deliver(data.isWithDelivery())
//                .deliveryPrice(420) // TODO: Has to be calculated and not hardcoded
//                .orderPrice(data.getTotalPrice())
//                .restaurantId(data.getRestaurantId())
//                .customer(customerEntity)
//                .systemOrderId(data.getId())
//                .status(data.getStatus())
//                .build();

        CustomerOrderEntity customerOrder = new CustomerOrderEntity(data);
        // save customer order
        log.info("Customer order entity successfully build in customer order service: {}",
                customerOrder);

        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public int updateSystemOrder(OrderStatus orderStatus, int id) {
        String orderStatusString = String.valueOf(orderStatus);
        log.info("Customer order status updated in customer order service to status {} with system id {}",
                orderStatus,
                id);
        return customerOrderRepository.setCustomerOrderStatus(orderStatusString, id);
    }

    public CartEntity addItemToCart(String customerId, CartEntity cartEntity) {
        int parsedCustomerId = Integer.parseInt(customerId);
        cartEntity.setCustomerId(parsedCustomerId);
        cartEntity = findCartForCustomer(cartEntity);

        cartEntity = restaurantCalculateTotalPrice(cartEntity);

        return cartRepository.save(cartEntity);
    }

    private CartEntity findCartForCustomer(CartEntity cartEntity) {
        return cartRepository.findById(cartEntity.getCustomerId()).orElse(cartRepository.save(cartEntity));
    }

    private CartEntity restaurantCalculateTotalPrice(CartEntity entity) {
        // todo: call restaurant service to calculate order price
//      rpcService.restaurantCalculateTotalPrice(entity);
        return entity;
    }

    public String purchaseOrder(String customerId) {
        int parsedCustomerId = Integer.parseInt(customerId);
        CartEntity cartEntity = cartRepository.findById(parsedCustomerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer has no active cart with the given id: " + customerId));
// TODO EITHER SAVE cart here or remove custoerId from cart and use normal id?
        cartEntity.setCustomerId(parsedCustomerId);
        cartRepository.save(cartEntity);

        return camundaService.startOrderProcess(customerId, new NewOrder(cartEntity));
    }

    // TODO PROBABLY DELETE :(:(:(
    public Optional<CustomerOrderEntity> findNewCustomerOrder(NewCustomerOrder order) {
        return Optional.ofNullable(customerOrderRepository.findById(order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer order was not found with the given customer order id: " + order.getId())
                ));
    }

    public void emptyCart(int customerId) {
        // TODO: return true or false
        cartRepository.deleteById(customerId);
    }
}
