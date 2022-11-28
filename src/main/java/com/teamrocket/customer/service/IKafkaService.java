package com.teamrocket.customer.service;

import com.teamrocket.customer.dto.NewCustomerEventDTO;

public interface IKafkaService {
    //    publishEvent(MessageRequest messageRequest);
    void publishEvent(NewCustomerEventDTO newCustomer);
}
