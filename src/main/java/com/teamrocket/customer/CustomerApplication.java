package com.teamrocket.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamrocket.customer.domain.model.dto.NewCustomer;
import com.teamrocket.customer.domain.model.dto.NewCustomerOrder;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }


    // TODO: REMOVE when it is time to deploy
    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, Object> kafkaTemplate) {
        return args -> {
            for (int i = 0; i < 2; i++) {
                kafkaTemplate.send("NEW_CUSTOMER", new NewCustomer("JP", "LM", "email@email.com", 12 + i, "12345678"));
            }
        };
    }

    NewCustomerOrder newCustomerOrder = NewCustomerOrder.builder()
            .id(14)
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
