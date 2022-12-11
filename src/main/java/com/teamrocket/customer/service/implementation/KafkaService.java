package com.teamrocket.customer.service.implementation;

import com.teamrocket.customer.service.IKafkaService;
import com.teamrocket.customer.model.dto.CustomerNotification;
import com.teamrocket.customer.model.dto.NewCustomer;
import com.teamrocket.customer.model.enums.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaService implements IKafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void newCustomerEvent(NewCustomer newCustomer) {
        String newCustomerTopic = String.valueOf(Topic.NEW_CUSTOMER);

        kafkaTemplate.send(newCustomerTopic, newCustomer);

        log.info("New customer registration event was successfully emitted from kafka service with unique email: {}",
                newCustomer.getEmail());
    }

    @Override
    public void customerNotificationEvent(CustomerNotification customerNotification) {
        kafkaTemplate.send(Topic.CUSTOMER_NOTIFICATION.name(), customerNotification);

        log.info("Customer notification event was successfully emitted with topic {} from kafka service to customer with unique email {}",
                Topic.CUSTOMER_NOTIFICATION.name(),
                customerNotification.getEmail());
    }
}
