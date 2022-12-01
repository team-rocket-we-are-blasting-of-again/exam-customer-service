package com.teamrocket.customer.config;

import com.teamrocket.customer.domain.model.dto.NewCustomer;
import com.teamrocket.customer.util.KafkaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaUtil kafkaUtil;

    @Bean
    public ConsumerFactory<String, NewCustomer> messageConsumerFactory() {
        return this.kafkaUtil.createClassConsumerFactory(NewCustomer.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NewCustomer>> messageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NewCustomer> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.messageConsumerFactory());
        return factory;
    }

}
