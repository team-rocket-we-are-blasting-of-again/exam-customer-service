package com.teamrocket.customer.listener;

import com.teamrocket.customer.model.dto.*;
import com.teamrocket.customer.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.model.enums.OrderStatus;
import com.teamrocket.customer.model.enums.Topic;
import com.teamrocket.customer.service.implementation.CustomerOrderService;
import com.teamrocket.customer.service.implementation.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListeners {
    private final CustomerService customerService;
    private final CustomerOrderService customerOrderService;

    @KafkaListener(
            topics = "NEW_ORDER_PLACED",
            groupId = "new-order-id" // unique id when scaling
    )
    void newOrderPlaceListener(NewCustomerOrder data) {
        log.info("Kafka listener with topic NEW_ORDER_PLACED was hit");
        // find customer by id
        CustomerDTO customer = customerService.getCustomerById(data.getCustomerId());

        log.info("New customer order listener successfully fetched customer with id {} from consumed event with topic {}",
                data.getCustomerId(),
                "NEW_ORDER_PLACED");

        // create and save customer order
        CustomerOrderEntity customerOrder = customerOrderService.createCustomerOrder(customer, data);

        log.info("New customer order listener successfully stored customer with email {} from consumed event with topic {}",
                customer.getEmail(),
                "NEW_ORDER_PLACED");

        customerOrderService.emptyCart(data.getCustomerId());

        log.info("Customers cart with email {} and with a total amount of {} products, was successfully emptied by customer id: {}",
                customerOrder.getCustomer().getEmail(),
                customerOrder.getCustomer().getCustomerOrder().size(),
                customerOrder.getCustomer().getId());

        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.PENDING, data.getId());

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.NEW_ORDER_PLACED);
    }

    @KafkaListener(
            topics = "ORDER_ACCEPTED",
            groupId = "order-accepted-id" // unique id when scaling
    )
    void orderAcceptedListener(SystemOrder data) {
        log.info("Kafka listener with topic ORDER_ACCEPTED was hit");
        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.IN_PROGRESS, data.getSystemOrderId());

        // find customer with system order id
        Optional<CustomerOrderEntity> customerOrder = customerOrderService.findCustomerOrderBySystemOrderId(data.getSystemOrderId());

        // built dto out of optional entity
        CustomerDTO customer = new CustomerDTO(customerOrder);

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.CUSTOMER_NOTIFICATION);
    }

    @KafkaListener(
            topics = "ORDER_READY",
            groupId = "order-reader-id" // unique id when scaling
    )
    void orderReadyListener(SystemOrder data) {
        log.info("Kafka listener with topic ORDER_READY was hit");
        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.READY, data.getSystemOrderId());

        // find customer with system order id
        Optional<CustomerOrderEntity> customerOrder = customerOrderService.findCustomerOrderBySystemOrderId(data.getSystemOrderId());

        // built dto out of optional entity
        CustomerDTO customer = new CustomerDTO(customerOrder);

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.CUSTOMER_NOTIFICATION);
    }

    @KafkaListener(
            topics = "ORDER_CANCELED",
            groupId = "order-cancelled-id" // unique id when scaling
    )
    void orderCanceledListener(OrderCancelled data) {
        log.info("Kafka listener with topic ORDER_CANCELED was hit");
        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.CANCELLED, data.getSystemOrderId());

        // find customer with system order id
        Optional<CustomerOrderEntity> customerOrder = customerOrderService.findCustomerOrderBySystemOrderId(data.getSystemOrderId());

        // built dto out of optional entity
        CustomerDTO customer = new CustomerDTO(customerOrder);

        // set cancellation reason
        customer.setReason(data.getReason());

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.CUSTOMER_NOTIFICATION);
    }

    @KafkaListener(
            topics = "ORDER_PICKED_UP",
            groupId = "order-picked-up-id" // unique id when scaling
    )
    void orderPickedUpListener(SystemOrder data) {
        log.info("Kafka listener with topic ORDER_PICKED_UP was hit");
        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.PICKED_UP, data.getSystemOrderId());

        // find customer with system order id
        Optional<CustomerOrderEntity> customerOrder = customerOrderService.findCustomerOrderBySystemOrderId(data.getSystemOrderId());

        // built dto out of optional entity
        CustomerDTO customer = new CustomerDTO(customerOrder);

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.CUSTOMER_NOTIFICATION);
    }

    @KafkaListener(
            topics = "ORDER_DELIVERED",
            groupId = "order-delivered-id" // unique id when scaling
    )
    void orderDeliveredListener(SystemOrder data) {
        log.info("Kafka listener with topic ORDER_DELIVERED was hit");
        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.COMPLETED, data.getSystemOrderId());

        // find customer with system order id
        Optional<CustomerOrderEntity> customerOrder = customerOrderService.findCustomerOrderBySystemOrderId(data.getSystemOrderId());

        // built dto out of optional entity
        CustomerDTO customer = new CustomerDTO(customerOrder);

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.CUSTOMER_NOTIFICATION);
    }

    @KafkaListener(
            topics = "ORDER_CLAIMED",
            groupId = "order-claimed-id" // unique id when scaling
    )
    void orderClaimedListener(SystemOrder data) {
        log.info("Kafka listener with topic ORDER_CLAIMED was hit");
        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.COMPLETED, data.getSystemOrderId());

        // find customer with system order id
        Optional<CustomerOrderEntity> customerOrder = customerOrderService.findCustomerOrderBySystemOrderId(data.getSystemOrderId());

        // built dto out of optional entity
        CustomerDTO customer = new CustomerDTO(customerOrder);

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.CUSTOMER_NOTIFICATION);
    }
}
