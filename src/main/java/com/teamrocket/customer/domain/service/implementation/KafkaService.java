package com.teamrocket.customer.domain.service.implementation;

import com.teamrocket.customer.domain.service.IKafkaService;
import com.teamrocket.customer.model.dto.CustomerNotification;
import com.teamrocket.customer.model.dto.NewCustomer;
import com.teamrocket.customer.model.enums.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService implements IKafkaService {
    @Autowired
    private KafkaTemplate<String, NewCustomer> kafkaTemplateCustomer;
    @Autowired
    private KafkaTemplate<String, CustomerNotification> kafkaTemplateNotification;

    @Override
    public void newCustomerEvent(NewCustomer newCustomer) {
        String newCustomerTopic = String.valueOf(Topic.NEW_CUSTOMER);
        kafkaTemplateCustomer.send(newCustomerTopic, newCustomer);
    }

    @Override
    public void customerNotificationEvent(Topic topic, CustomerNotification customerNotification) {
        kafkaTemplateNotification.send(topic.name(), customerNotification);
    }
}
