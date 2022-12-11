package com.teamrocket.customer;

import com.teamrocket.customer.model.dto.NewCustomer;
import com.teamrocket.customer.model.dto.NewCustomerOrder;
import com.teamrocket.customer.model.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
        log.info("Spring application was successfully started: {}",
                LocalTime.now());
    }


    // TODO: For testing, remove when moving to production
    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, Object> kafkaTemplate) {
        return args -> {
            for (int i = 0; i < 2; i++) {
                kafkaTemplate.send("NEW_CUSTOMER", new NewCustomer("JP", "LM", "email@email.com", 12 + i, "12345678"));
            }
        };
    }

    // TODO: For testing, remove when moving to production
    NewCustomerOrder newCustomerOrder = NewCustomerOrder.builder()
            .id(36)
            .restaurantId(1)
            .customerId(1)
            .createdAt(new Date())
            .status(OrderStatus.PENDING)
            .withDelivery(true)
            .totalPrice(1337)
            .items(new ArrayList<>())
            .build();

    @Bean
    CommandLineRunner commandLineRunnerNewOrder(KafkaTemplate<String, Object> kafkaTemplateOrderEventTest) {
        return args -> kafkaTemplateOrderEventTest.send("NEW_ORDER_PLACED", newCustomerOrder);
    }


}
