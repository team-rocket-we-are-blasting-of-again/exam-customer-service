package com.teamrocket.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic customerTopic() {
        return TopicBuilder.name("new-customer-topic")
                .build();
    }
}
