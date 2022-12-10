package com.teamrocket.customer.domain.service.implementation;

import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.dto.NewOrder;
import com.teamrocket.customer.domain.model.entity.CartEntity;
import com.teamrocket.customer.domain.model.entity.CartItemEntity;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import com.teamrocket.customer.domain.service.ICustomerOrderService;
import com.teamrocket.customer.exceptions.ResourceNotFoundException;
import com.teamrocket.customer.infrastructure.repository.CartRepository;
import com.teamrocket.customer.infrastructure.repository.CustomerOrderRepository;
import com.teamrocket.customer.infrastructure.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerOrderService implements ICustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantClient restaurantClient;
    private final CamundaService camundaService;

    @Override
    @Transactional
    public CustomerOrderEntity createCustomerOrder(CustomerDTO customer, NewCustomerOrder data) {
        // Missing implementation for delivery pricing, should be done in order service
        // and location service to give a price depending on your location
        // for now it is 55kr :)
        data.setDeliveryPrice(55.0);

        CustomerOrderEntity customerOrder = new CustomerOrderEntity(data);

        log.info("Customer order entity successfully build in customer order service: {}",
                customerOrder);

        CustomerEntity customerEntity = customerRepository.findById(data.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer was not found with the provided id: "
                        + data.getCustomerId()));

        // add to customer order entity
        customerOrder.setCustomer(customerEntity);
        // add to customer entity
        customerEntity.getCustomerOrder().add(customerOrder);
        // save customer order
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    @Transactional
    public int updateSystemOrder(OrderStatus orderStatus, int id) {
        log.info("Customer order status updated in customer order service to status {} with system id {}",
                orderStatus,
                id);
        return customerOrderRepository.setCustomerOrderStatus(orderStatus, id);
    }

    @Override
    public CartEntity addItemToCart(String customerId, CartEntity cartEntity) {
        int parsedCustomerId = Integer.parseInt(customerId);

        cartEntity.setCustomerId(parsedCustomerId);
        CartEntity newCustomerCartEntity = findCartForCustomer(cartEntity);

        log.info("Cart for customer with id {} was found",
                cartEntity.getCustomerId());

        newCustomerCartEntity.setTotalPrice(restaurantClient.restaurantCalculateTotalPrice(cartEntity));

        newCustomerCartEntity.getItems().clear();

        for (CartItemEntity item : cartEntity.getItems()) {
            newCustomerCartEntity.getItems().add(item);

            log.info("Adding item {} to cart with with customer id {}",
                    item,
                    customerId);
        }


        log.info("Customer cart was successfully saved in database with cart properties: {} and customer id: {}",
                cartEntity,
                customerId);

        return cartRepository.save(newCustomerCartEntity);
    }

    private CartEntity findCartForCustomer(CartEntity cartEntity) {
        return cartRepository.findById(cartEntity.getCustomerId()).orElseGet(() -> cartRepository.save(cartEntity));
    }

    @Override
    public String purchaseOrder(String customerId) {
        int parsedCustomerId = Integer.parseInt(customerId);
        CartEntity cartEntity = cartRepository.findById(parsedCustomerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer has no active cart with the given id: " + customerId));

        log.info("Purchase order method from customer order service successfully found customer cart with customer id: {}",
                customerId);

        cartEntity.setCustomerId(parsedCustomerId);

        log.info("Customer id was set on cart entity with customer id: {}",
                parsedCustomerId);

        cartRepository.save(cartEntity);

        log.info("Customer cart was successfully saved in purchase order method in customer order service.");

        return camundaService.startOrderProcess(customerId, new NewOrder(cartEntity));
    }

    @Override
    public Optional<CustomerOrderEntity> findCustomerOrderBySystemOrderId(int systemOrderId) {
        Optional<CustomerOrderEntity> customerOrderEntity = Optional.of(customerOrderRepository.findCustomerOrderEntityBySystemOrderId(systemOrderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer order was not found with the given customer order system id: " + systemOrderId)
                ));

        log.info("Customer order with system order id {} was successfully fetched from customer order service", systemOrderId);

        return customerOrderEntity;
    }

    @Override
    public Map<String, Boolean> emptyCart(int customerId) {
        cartRepository.deleteById(customerId);

        log.info("Customer cart was successfully deleted with customer id: {}", customerId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }
}
