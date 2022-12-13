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

}
