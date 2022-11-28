package com.teamrocket.customer.service.implementation;

import com.teamrocket.customer.dto.NewCustomerEventDTO;
import com.teamrocket.customer.service.IKafkaService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService implements IKafkaService {

    private final KafkaTemplate<String, NewCustomerEventDTO> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, NewCustomerEventDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(NewCustomerEventDTO newCustomer) {
        String newCustomerTopic = "NEW_CUSTOMER";
        kafkaTemplate.send(newCustomerTopic, newCustomer);
    }
    // TODO: delete
//    public void publishEvent(MessageRequest messageRequest) {
//        kafkaTemplate.send("NEW_CUSTOMER", messageRequest.message());
//    }
}
