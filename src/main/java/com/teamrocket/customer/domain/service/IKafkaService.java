package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.model.dto.CustomerNotification;
import com.teamrocket.customer.model.dto.NewCustomer;
import com.teamrocket.customer.model.enums.Topic;

public interface IKafkaService {
    void newCustomerEvent(NewCustomer newCustomer);

    void customerNotificationEvent(Topic topic, CustomerNotification customerNotification);
}
