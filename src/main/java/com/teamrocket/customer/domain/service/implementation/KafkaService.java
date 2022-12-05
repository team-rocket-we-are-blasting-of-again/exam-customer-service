package com.teamrocket.customer.domain.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    //    @Autowired
//    private KafkaTemplate<String, NewCustomer> kafkaTemplateCustomer;

    private final KafkaTemplate<String, CustomerNotification> kafkaTemplateNotification;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void newCustomerEvent(NewCustomer newCustomer) {
        String newCustomerTopic = String.valueOf(Topic.NEW_CUSTOMER);
        String newCustomerString = null;

        try {
            newCustomerString = objectMapper.writeValueAsString(newCustomer);
        } catch (JsonProcessingException exception) {
            log.error("An object mapper exception has occurred: {}",
                    "from new customer event",
                    new RuntimeException(exception)
            );
        }

        kafkaTemplate.send(newCustomerTopic, newCustomerString);

        log.info("New customer registration event was successfully emitted from kafka service with unique email: {}",
                newCustomer.getEmail());
    }

    @Override
    public void customerNotificationEvent(Topic topic, CustomerNotification customerNotification) {
        String newCustomerNotificationString = null;

        try {
            newCustomerNotificationString = objectMapper.writeValueAsString(customerNotification);
        } catch (JsonProcessingException exception) {
            log.error("An object mapper exception has occurred: {}",
                    "from new customer order notification event",
                    new RuntimeException(exception)
            );
        }

        kafkaTemplate.send(topic.name(), newCustomerNotificationString);

        log.info("Customer notification event was successfully emitted with topic {} from kafka service to customer with unique email {}",
                topic,
                customerNotification.getEmail());
    }

//    @Override
//    public void customerNotificationEvent(Topic topic, CustomerNotification customerNotification) {
//        String customerNotificationString = null;
//        try {
//            customerNotificationString = objectMapper.writeValueAsString(customerNotification);
//        } catch (JsonProcessingException exception) {
//            log.error("An object mapper exception has occurred: {}",
//                    "from customer notification event",
//                    new RuntimeException(exception)
//            );
//        }
//        kafkaTemplate.send(topic.name(), customerNotificationString);
//    }
//    @Override
//    public void newCustomerEvent(NewCustomer newCustomer) {
//        String newCustomerTopic = String.valueOf(Topic.NEW_CUSTOMER);
//        kafkaTemplateCustomer.send(newCustomerTopic, newCustomer);
//    }

//    @Override
//    public void customerNotificationEvent(Topic topic, CustomerNotification customerNotification) {
//        kafkaTemplateNotification.send(topic.name(), customerNotification);
//    }
}
