package com.teamrocket.customer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(
            topics = "new-customer-topic",
            groupId = "groupId" // unique id when scaling
    )
    void listener(String data) {
        System.out.println("new-customer-topic listener received: " + data);
    }
}
