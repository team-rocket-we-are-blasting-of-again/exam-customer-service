package com.teamrocket.customer;

import com.teamrocket.customer.model.dto.NewCustomer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    // TODO: REMOVE when it is time to deploy
    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, NewCustomer> kafkaTemplate) {
        return args -> {
            for (int i = 0; i < 2; i++) {
                kafkaTemplate.send("NEW_CUSTOMER", new NewCustomer("JP", "LM", "email@email.com", 12 + i, "12345678"));
            }
        };
    }

}
