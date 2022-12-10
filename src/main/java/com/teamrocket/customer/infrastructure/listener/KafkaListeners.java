package com.teamrocket.customer.infrastructure.listener;

import com.teamrocket.customer.domain.model.dto.*;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import com.teamrocket.customer.domain.model.enums.Topic;
import com.teamrocket.customer.domain.service.implementation.CustomerOrderService;
import com.teamrocket.customer.domain.service.implementation.CustomerService;
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

    // TODO: For testing, remove when moving to production
    @KafkaListener(
            topics = "NEW_CUSTOMER",
            groupId = "customerId" // Unique id when scaling
    )
    void listener(NewCustomer data) {

        System.out.printf("Topic: NEW_CUSTOMER listener received %n%s%n%s%n%s%n%s%n%s%n: ",
                data.getFirstName(),
                data.getLastName(),
                data.getEmail(),
                data.getAddressId(),
                data.getPhone());

    }

    // TODO: For testing, remove when moving to production
    @KafkaListener(
            topics = "CUSTOMER_NOTIFICATION",
            groupId = "customerId" // Unique id when scaling
    )
    void customerNotificationListener(CustomerNotification data) {
        System.out.printf("Topic: NEW_CUSTOMER listener received %n%s%n%s%n%s: ",
                data.getEmail(),
                data.getSubject(),
                data.getMessage());
    }

    // TODO: Update status on customer order in database and order dto
    @KafkaListener(
            topics = "NEW_ORDER_PLACED",
            groupId = "new-order-id" // unique id when scaling
    )
    void newOrderPlaceListener(NewCustomerOrder data) {
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
           // set status on order in db
        customerOrderService.updateSystemOrder(OrderStatus.IN_PROGRESS, data.getSystemOrderId());

        // find customer with system order id
        Optional<CustomerOrderEntity> customerOrder = customerOrderService.findCustomerOrderBySystemOrderId(data.getSystemOrderId());

        // built dto out of optional entity
        CustomerDTO customer = new CustomerDTO(customerOrder);

        // emit customer notification event
        customerService.notifyCustomer(customer, Topic.CUSTOMER_NOTIFICATION);
    }
//
//    @KafkaListener(
//            topics = "ORDER_READY",
//            groupId = "customerId" // unique id when scaling
//    )
//    void orderReadyListener(SystemOrder data) {
//        int systemOrderId = data.getSystemOrderId();
//
//        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.READY, systemOrderId);
//
//        CustomerNotification customerNotification = CustomerNotification.builder()
//                .email(customerOrder.getCustomer().getEmail())
//                .subject("MTOGO: Order is ready for pickup.")
//                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
//                        + ",%n" + "The restaurant has now finished your order and is ready to be enjoyed.")
//                .build();
//
//        kafkaService.customerNotificationEvent(Topic.ORDER_READY, customerNotification);
//    }
//
//    @KafkaListener(
//            topics = "ORDER_CANCELED",
//            groupId = "customerId" // unique id when scaling
//    )
//    void orderCanceledListener(OrderCancelled data) {
//        int systemOrderId = data.getSystemOrderId();
//
//        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.CANCELLED, systemOrderId);
//
//        CustomerNotification customerNotification = CustomerNotification.builder()
//                .email(customerOrder.getCustomer().getEmail())
//                .subject("MTOGO: Order has been cancelled.")
//                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
//                        + ",%n" + "Your order has been cancelled. The reason for this is: " + data.getReason()
//                        + "%nIf you did not cancel your order please try again or feel free to contact our customer support for further " +
//                        "information or questions you might have.")
//                .build();
//
//        kafkaService.customerNotificationEvent(Topic.ORDER_CANCELED, customerNotification);
//    }
//
//    @KafkaListener(
//            topics = "ORDER_PICKED_UP",
//            groupId = "customerId" // unique id when scaling
//    )
//    void orderPickedUpListener(SystemOrder data) {
//        int systemOrderId = data.getSystemOrderId();
//
//        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.PICKED_UP, systemOrderId);
//
//        CustomerNotification customerNotification = CustomerNotification.builder()
//                .email(customerOrder.getCustomer().getEmail())
//                .subject("MTOGO: Order has been picked up.")
//                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
//                        + ",%n" + "You order has been picked up by our courier and will be with your shortly.")
//                .build();
//
//        kafkaService.customerNotificationEvent(Topic.ORDER_PICKED_UP, customerNotification);
//    }
//
//    @KafkaListener(
//            topics = "ORDER_DELIVERED",
//            groupId = "customerId" // unique id when scaling
//    )
//    void orderDeliveredListener(SystemOrder data) {
//        int systemOrderId = data.getSystemOrderId();
//
//        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.COMPLETED, systemOrderId);
//
//        CustomerNotification customerNotification = CustomerNotification.builder()
//                .email(customerOrder.getCustomer().getEmail())
//                .subject("MTOGO: Order has been delivered.")
//                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
//                        + ",%n" + "Your order has been delivered by our courier." + ".%nThank you for your order and we hope our service was as expected."
//                        + "%nYour receipt will look as the following: "
//                        + "Delivery cost: " + customerOrder.getDeliveryPrice() + "%nTotal order price: " + customerOrder.getOrderPrice()
//                        + "Kind regards"
//                        + "%n%n"
//                        + "MTOGO A/S")
//                .build();
//
//        kafkaService.customerNotificationEvent(Topic.ORDER_DELIVERED, customerNotification);
//    }
//
//    @KafkaListener(
//            topics = "ORDER_CLAIMED",
//            groupId = "customerId" // unique id when scaling
//    )
//    void orderClaimedListener(SystemOrder data) {
//        int systemOrderId = data.getSystemOrderId();
//
//        CustomerOrderEntity customerOrder = customerOrderService.updateSystemOrder(OrderStatus.CLAIMED, systemOrderId);
//
//        CustomerNotification customerNotification = CustomerNotification.builder()
//                .email(customerOrder.getCustomer().getEmail())
//                .subject("MTOGO: Order has been claimed.")
//                .message("Dear " + customerOrder.getCustomer().getFirstName() + " " + customerOrder.getCustomer().getLastName()
//                        + ",%n" + "You order has been claimed by one of our couriers.")
//                .build();
//
//        kafkaService.customerNotificationEvent(Topic.ORDER_CLAIMED, customerNotification);
//    }

}
