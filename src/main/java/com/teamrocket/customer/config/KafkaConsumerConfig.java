package com.teamrocket.customer.config;

import com.teamrocket.customer.dto.TemplateDTO;
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
    // TODO: IMPLEMENT REAL DTO
    @Bean
    public ConsumerFactory<String, TemplateDTO> messageConsumerFactory() {
        return this.kafkaUtil.createClassConsumerFactory(TemplateDTO.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, TemplateDTO>> messageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TemplateDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.messageConsumerFactory());
        return factory;
    }

}
