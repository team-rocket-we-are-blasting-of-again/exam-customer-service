package com.teamrocket.customer.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic customerTopic() {
        return TopicBuilder.name("NEW_CUSTOMER")
                .replicas(1)
                .partitions(3)
                .build();
    }
    @Bean
    public NewTopic newOrderPlacedTopic() {
        return TopicBuilder.name("NEW_ORDER_PLACED")
                .build();
    }

    @Bean
    public NewTopic customerNotificationTopic() {
        return TopicBuilder.name("CUSTOMER_NOTIFICATION")
                .build();
    }
}
