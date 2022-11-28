package com.teamrocket.customer.config;

import com.teamrocket.customer.dto.NewCustomerEventDTO;
import com.teamrocket.customer.model.Customer;
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

    // TODO: PASS A CUSTOMER OR ORDER DTO AS VALUE
    @Bean
    public ProducerFactory<String, NewCustomerEventDTO> producerFactory() {
        return this.kafkaUtil.createClassProducerFactory();
    }

    // TODO: PASS A CUSTOMER OR ORDER DTO AS VALUE
    @Bean
    public KafkaTemplate<String, NewCustomerEventDTO> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }

}
