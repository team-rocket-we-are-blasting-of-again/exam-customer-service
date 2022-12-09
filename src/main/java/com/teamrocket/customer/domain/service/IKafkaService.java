package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.domain.model.dto.CustomerNotification;
import com.teamrocket.customer.domain.model.dto.NewCustomer;

public interface IKafkaService {
    void newCustomerEvent(NewCustomer newCustomer);

    void customerNotificationEvent(CustomerNotification customerNotification);
}
