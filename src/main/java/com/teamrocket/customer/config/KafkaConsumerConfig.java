package com.teamrocket.customer.config;

import com.teamrocket.customer.domain.model.dto.CustomerNotification;
import com.teamrocket.customer.domain.model.dto.NewCustomer;
import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
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
    public ConsumerFactory<String, String> messageConsumerFactory() {
        return this.kafkaUtil.createClassConsumerFactory(String.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> messageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.messageConsumerFactory());
        return factory;
    }

//    @Bean
//    public ConsumerFactory<String, NewCustomer> messageConsumerFactoryNewCustomer() {
//        return this.kafkaUtil.createClassConsumerFactory(NewCustomer.class);
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NewCustomer>> messageFactoryNewCustomer() {
//        ConcurrentKafkaListenerContainerFactory<String, NewCustomer> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(this.messageConsumerFactoryNewCustomer());
//        return factory;
//    }

    /**
     * NewCustomerOrder
     */
    @Bean
    public ConsumerFactory<String, NewCustomerOrder> messageConsumerFactoryNewCustomerOrder() {
        return this.kafkaUtil.createClassConsumerFactory(NewCustomerOrder.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NewCustomerOrder>> messageFactoryNewCustomerOrder() {
        ConcurrentKafkaListenerContainerFactory<String, NewCustomerOrder> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.messageConsumerFactoryNewCustomerOrder());
        return factory;
    }

    /**
     * Customer notification event
     */
    @Bean
    public ConsumerFactory<String, CustomerNotification> messageConsumerFactoryCustomerNotification() {
        return this.kafkaUtil.createClassConsumerFactory(CustomerNotification.class);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CustomerNotification>> messageFactoryCustomerNotification() {
        ConcurrentKafkaListenerContainerFactory<String, CustomerNotification> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.messageConsumerFactoryCustomerNotification());
        return factory;
    }

}
