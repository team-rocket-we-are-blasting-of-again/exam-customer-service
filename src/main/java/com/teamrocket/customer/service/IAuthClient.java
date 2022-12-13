package com.teamrocket.customer.service;


import com.google.protobuf.Descriptors;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.model.entity.CustomerEntity;

import java.util.Map;

public interface IAuthClient {
    Map<Descriptors.FieldDescriptor, Object> createCustomer(CustomerEntity customer, CustomerRegistrationRequest request);
}
