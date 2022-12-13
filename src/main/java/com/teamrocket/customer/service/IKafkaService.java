package com.teamrocket.customer.service;

import com.teamrocket.customer.model.dto.CustomerNotification;
import com.teamrocket.customer.model.dto.NewCustomer;

public interface IKafkaService {
    void newCustomerEvent(NewCustomer newCustomer);

    void customerNotificationEvent(CustomerNotification customerNotification);
}
