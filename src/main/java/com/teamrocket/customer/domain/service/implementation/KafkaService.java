package com.teamrocket.customer.domain.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.service.IKafkaService;
import com.teamrocket.customer.domain.model.dto.CustomerNotification;
import com.teamrocket.customer.domain.model.dto.NewCustomer;
import com.teamrocket.customer.domain.model.enums.Topic;
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
