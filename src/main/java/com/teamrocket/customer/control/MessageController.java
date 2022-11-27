package com.teamrocket.customer.control;

import com.teamrocket.customer.model.MessageRequest;
import com.teamrocket.customer.service.KafkaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class MessageController {

    private KafkaService kafkaService;

    public MessageController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/event")
    public void publishEvent(@RequestBody MessageRequest messageRequest) {
        kafkaService.publishEvent(messageRequest);
    }
}
