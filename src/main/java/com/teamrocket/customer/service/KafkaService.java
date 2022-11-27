package com.teamrocket.customer.service;

import com.teamrocket.customer.model.MessageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(MessageRequest messageRequest) {
        kafkaTemplate.send("new-customer-topic", messageRequest.message());
    }
}
