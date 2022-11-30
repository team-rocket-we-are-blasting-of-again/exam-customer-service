package com.teamrocket.customer.infrastructure.listener;

import com.teamrocket.customer.model.dto.*;
import com.teamrocket.customer.model.enums.Topic;
import com.teamrocket.customer.domain.service.implementation.KafkaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private KafkaService kafkaService;

    public KafkaListeners(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
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
        // FIND customer from customerid from order
        // TODO:Emit object (CustomerNotification) email, subject, message : CUSTOMER_NOTIFICATION

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email("EMAIL IS MISSING")
                .subject("MTOGO: Order has been placed")
                .message("Dear " + "data.getName() " + ",%n" + "Thank you for your order. We will begin the process og validating your order.")
                .build();

        kafkaService.customerNotificationEvent(Topic.NEW_ORDER_PLACED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_ACCEPTED",
            groupId = "customerId" // unique id when scaling
    )
    void orderAcceptedListener(SystemOrder data) {
        // FIND customer from customerid from order
        // TODO:Emit object (CustomerNotification) email, subject, message : CUSTOMER_NOTIFICATION

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email("EMAIL IS MISSING")
                .subject("MTOGO: Order has been accepted")
                .message("Dear " + "data.getName() " + ",%n" + "Your order has been accepted and we will inform you when the order is ready.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_ACCEPTED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_READY",
            groupId = "customerId" // unique id when scaling
    )
    void orderReadyListener(SystemOrder data) {
        // FIND customer from customerid from order
        // TODO:Emit object (CustomerNotification) email, subject, message : CUSTOMER_NOTIFICATION

        CustomerNotification customerNotification = CustomerNotification.builder()
                .email("EMAIL IS MISSING")
                .subject("MTOGO: Order is ready for pickup")
                .message("Dear " + "data.getName() " + ",%n" + "The restaurant has finished your order and is ready for pick up.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_READY, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_CANCELED",
            groupId = "customerId" // unique id when scaling
    )
    void orderCanceledListener(OrderCancelled data) {
        // FIND customer from customerid from order
        // TODO:Emit object (CustomerNotification) email, subject, message : CUSTOMER_NOTIFICATION
        CustomerNotification customerNotification = CustomerNotification.builder()
                .email("EMAIL IS MISSING")
                .subject("MTOGO: Order has been cancelled")
                .message("Dear " + "data.getName() " + ",%n" + "Your order has been cancelled. The reason for this is: " + data.getReason() + "%nPlease try again or contact our customer support for further information or questions you might have.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_CANCELED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_PICKED_UP",
            groupId = "customerId" // unique id when scaling
    )
    void orderPickedUpListener(SystemOrder data) {
        // FIND customer from customerid from order
        // TODO:Emit object (CustomerNotification) email, subject, message : CUSTOMER_NOTIFICATION
        CustomerNotification customerNotification = CustomerNotification.builder()
                .email("EMAIL IS MISSING")
                .subject("MTOGO: Order has been picked up.")
//                .message("Dear " + data.getCustomerName() + ",%n" + "Your order has been picked up at " + data.getPickUpTime()
//                        + " by " + data.getCourier()
//                        + "%nThe order will arrive approximately: " + data.getDropOffTime())
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_PICKED_UP, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_DELIVERED",
            groupId = "customerId" // unique id when scaling
    )
    void orderDeliveredListener(SystemOrder data) {
        // FIND customer from customerid from order
        // TODO:Emit object (CustomerNotification) email, subject, message : CUSTOMER_NOTIFICATION
        CustomerNotification customerNotification = CustomerNotification.builder()
                .email("EMAIL IS MISSING")
                .subject("MTOGO: Order has been delivered")
//                .message("Dear " + data.getCustomerName() + ",%n" + "Your order has been delivered by " + data.getCourier().getFirstName()
//                        + " " + data.getCourier().getLastName() + ".%nThank you for your order and we hope the service was as expected.")
                .build();

        kafkaService.customerNotificationEvent(Topic.ORDER_DELIVERED, customerNotification);
    }

    @KafkaListener(
            topics = "ORDER_CLAIMED",
            groupId = "customerId" // unique id when scaling
    )
    void orderClaimedListener(SystemOrder data) {
        // SHOULD A CUSTOMER REALLY BE NOTIFIED HERE?
        System.out.println("Topic: ORDER_CLAIMED listener received: " + data);
    }

}
