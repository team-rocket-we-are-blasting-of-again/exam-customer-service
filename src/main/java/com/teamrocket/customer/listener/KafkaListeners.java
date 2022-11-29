package com.teamrocket.customer.listener;

import com.teamrocket.customer.dto.NewCustomerEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(
            topics = "NEW_CUSTOMER",
            groupId = "customerId", // unique id when scaling
            containerFactory = "messageFactory" // to be able to consume objects @bean
    )
    void listener(NewCustomerEventDTO data) {
        System.out.printf("Topic: NEW_CUSTOMER listener received %n%s%n%s%n%s%n%s%n%s%n: ", data.firstName(), data.lastName(), data.email(), data.addressId(), data.phone());
    }

    // TODO: Update status on customer order in database and order dto
    @KafkaListener(
            topics = "NEW_ORDER_PLACED",
            groupId = "customerId" // unique id when scaling
    )
    void newOrderPlaceListener(NewCustomerEventDTO data) {
        // FIND customer from customerid from order
        // TODO:Emit object (CustomerNotification) email, subject, message : CUSTOMER_NOTIFICATION
        System.out.println("Topic: NEW_ORDER_PLACED listener received: " + data);
    }

    @KafkaListener(
            topics = "ORDER_ACCEPTED",
            groupId = "customerId" // unique id when scaling
    )
    void orderAcceptedListener(NewCustomerEventDTO data) {
        System.out.println("Topic: ORDER_ACCEPTED listener received: " + data);
    }

    @KafkaListener(
            topics = "ORDER_READY",
            groupId = "customerId" // unique id when scaling
    )
    void orderReadyListener(NewCustomerEventDTO data) {
        System.out.println("Topic: ORDER_READY listener received: " + data);
    }

    @KafkaListener(
            topics = "ORDER_CANCELED",
            groupId = "customerId" // unique id when scaling
    )
    void orderCanceledListener(NewCustomerEventDTO data) {
        System.out.println("Topic: ORDER_CANCELED listener received: " + data);
    }

    @KafkaListener(
            topics = "ORDER_PICKED_UP",
            groupId = "customerId" // unique id when scaling
    )
    void orderPickedUpListener(NewCustomerEventDTO data) {
        System.out.println("Topic: ORDER_PICKED_UP listener received: " + data);
    }

    @KafkaListener(
            topics = "ORDER_DELIVERED",
            groupId = "customerId" // unique id when scaling
    )
    void orderDeliveredListener(NewCustomerEventDTO data) {
        System.out.println("Topic: ORDER_DELIVERED listener received: " + data);
    }

    @KafkaListener(
            topics = "ORDER_CLAIMED",
            groupId = "customerId" // unique id when scaling
    )
    void orderClaimedListener(NewCustomerEventDTO data) {
        System.out.println("Topic: ORDER_CLAIMED listener received: " + data);
    }

}
