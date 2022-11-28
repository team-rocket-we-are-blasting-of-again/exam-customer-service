package com.teamrocket.customer.config;

import com.teamrocket.customer.dto.NewCustomerEventDTO;
import com.teamrocket.customer.model.Customer;
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

    // TODO: IMPLEMENT REAL CUSTOMERDTO - objekt fra kafka
    @Bean
    public ConsumerFactory<String, NewCustomerEventDTO> messageConsumerFactory() {
        return this.kafkaUtil.createClassConsumerFactory(NewCustomerEventDTO.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NewCustomerEventDTO>> messageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NewCustomerEventDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.messageConsumerFactory());
        return factory;
    }

}
