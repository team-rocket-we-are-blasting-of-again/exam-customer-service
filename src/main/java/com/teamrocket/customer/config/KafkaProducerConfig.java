package com.teamrocket.customer.config;

import com.teamrocket.customer.domain.model.dto.CustomerNotification;
import com.teamrocket.customer.domain.model.dto.NewCustomer;
import com.teamrocket.customer.util.KafkaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final KafkaUtil kafkaUtil;

    @Bean
    public ProducerFactory<String, NewCustomer> producerFactory() {
        return this.kafkaUtil.createClassProducerFactory();
    }

    @Bean
    public KafkaTemplate<String, NewCustomer> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }

    /**
     * Customer notification for notification service
     */
    @Bean
    public ProducerFactory<String, CustomerNotification> producerFactoryNotification() {
        return this.kafkaUtil.createClassProducerFactory();
    }

    @Bean
    public KafkaTemplate<String, CustomerNotification> kafkaTemplateNotification() {
        return new KafkaTemplate<>(this.producerFactoryNotification());
    }

}
