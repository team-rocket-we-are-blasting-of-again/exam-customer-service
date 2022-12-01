package com.teamrocket.customer.application.listener;

import com.teamrocket.customer.domain.model.dto.*;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import com.teamrocket.customer.domain.model.enums.Topic;
import com.teamrocket.customer.domain.service.implementation.CustomerOrderService;
import com.teamrocket.customer.domain.service.implementation.CustomerService;
import com.teamrocket.customer.domain.service.implementation.KafkaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
public class KafkaListeners {
    private final CustomerService customerService;
    private final KafkaService kafkaService;
    private final CustomerOrderService customerOrderService;

    public KafkaListeners(CustomerService customerService, KafkaService kafkaService, CustomerOrderService customerOrderService) {
        this.customerService = customerService;
        this.kafkaService = kafkaService;
        this.customerOrderService = customerOrderService;
    }

    // TODO: For testing, remove when moving to production
    @KafkaListener(
            topics = "NEW_CUSTOMER",
            groupId = "customerId", // Unique id when scaling
            containerFactory = "messageFactory" // KafkaConsumeConfig method name
    )
    void listener(NewCustomer data) {
        System.out.printf("Topic: NEW_CUSTOMER listener received %n%s%n%s%n%s%n%s%n%s%n: ",
                data.firstName(),
                data.lastName(),
                data.email(),
                data.addressId(),
                data.phone());
    }

    // TODO: Update status on customer order in database and order dto
    @KafkaListener(
            topics = "NEW_ORDER_PLACED",
            groupId = "customerId" // unique id when scaling
    )
    void newOrderPlaceListener(NewCustomerOrder data) {
        // find customer by id
        CustomerEntity customer = customerService.getCustomerById(data.getCustomerId());
        // create and save customer order
        CustomerOrderEntity customerOrder = customerOrderService.createCustomerOrder(customer, data);
        // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.PENDING, data.getId()); // TODO SKAL JEG BRUGE DENNE ENTITY TIL AT BYGGE NOTIFICATION?
        // build notification
        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customerOrder.getCustomer().getEmail())
                .subject("MTOGO: New order has been placed")
                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
                        + ",%n" + "Thank you for your order. We will begin the process of validating your order and will keep you updated throughout the whole process.")
                .build();
        // emit customer notification event
        kafkaService.customerNotificationEvent(Topic.NEW_ORDER_PLACED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_ACCEPTED",
            groupId = "customerId" // unique id when scaling
    )
    void orderAcceptedListener(SystemOrder data) {
        int systemOrderId = data.getSystemOrderId();

        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.IN_PROGRESS, systemOrderId);

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customerOrder.getCustomer().getEmail())
                .subject("MTOGO: Order has been accepted")
                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
                        + ",%n" + "Your order has been accepted and creation started at " + customerOrder.getCreatedAt() +
                        " and we will inform you when the order is ready.%n")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_ACCEPTED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_READY",
            groupId = "customerId" // unique id when scaling
    )
    void orderReadyListener(SystemOrder data) {
        int systemOrderId = data.getSystemOrderId();

        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.READY, systemOrderId);

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customerOrder.getCustomer().getEmail())
                .subject("MTOGO: Order is ready for pickup.")
                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
                        + ",%n" + "The restaurant has now finished your order and is ready to be enjoyed.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_READY, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_CANCELED",
            groupId = "customerId" // unique id when scaling
    )
    void orderCanceledListener(OrderCancelled data) {
        int systemOrderId = data.getSystemOrderId();

        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.CANCELLED, systemOrderId);

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customerOrder.getCustomer().getEmail())
                .subject("MTOGO: Order has been cancelled.")
                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
                        + ",%n" + "Your order has been cancelled. The reason for this is: " + data.getReason()
                        + "%nIf you did not cancel your order please try again or feel free to contact our customer support for further " +
                        "information or questions you might have.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_CANCELED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_PICKED_UP",
            groupId = "customerId" // unique id when scaling
    )
    void orderPickedUpListener(SystemOrder data) {
        int systemOrderId = data.getSystemOrderId();

        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.PICKED_UP, systemOrderId);

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customerOrder.getCustomer().getEmail())
                .subject("MTOGO: Order has been picked up.")
                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
                        + ",%n" + "You order has been picked up by our courier and will be with your shortly.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_PICKED_UP, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_DELIVERED",
            groupId = "customerId" // unique id when scaling
    )
    void orderDeliveredListener(SystemOrder data) {
        int systemOrderId = data.getSystemOrderId();

        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.COMPLETED, systemOrderId);

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customerOrder.getCustomer().getEmail())
                .subject("MTOGO: Order has been delivered.")
                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
                        + ",%n" + "Your order has been delivered by our courier." + ".%nThank you for your order and we hope our service was as expected."
                        + "%nYour receipt will look as the following: "
                        + "Delivery cost: " + customerOrder.getDeliveryPrice() + "%nTotal order price: " + customerOrder.getOrderPrice()
                        + "Kind regards"
                        + "%n%n"
                        + "MTOGO A/S")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_DELIVERED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_CLAIMED",
            groupId = "customerId" // unique id when scaling
    )
    void orderClaimedListener(SystemOrder data) {
        int systemOrderId = data.getSystemOrderId();

        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.CLAIMED, systemOrderId);

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email(customerOrder.getCustomer().getEmail())
                .subject("MTOGO: Order has been claimed.")
                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
                        + ",%n" + "You order has been claimed.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_CLAIMED, customerNotification);
    }

}
