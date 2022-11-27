package com.teamrocket.customer.config;

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

//    public Map<String, Object> producerConfig() {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return properties;
//    }

    // TODO: PASS A CUSTOMER OR ORDER AS VALUE INSTEAD OF STRING
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return this.kafkaUtil.createClassProducerFactory();
    }

//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }

    // TODO: PASS A CUSTOMER OR ORDER AS VALUE INSTEAD OF STRING
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate(
//            ProducerFactory<String, String> producerFactory
//    ) {
//        return new KafkaTemplate<>(producerFactory);
//    }

}
