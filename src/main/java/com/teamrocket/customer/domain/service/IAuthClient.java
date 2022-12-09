package com.teamrocket.customer.domain.service;


import com.google.protobuf.Descriptors;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;

import java.util.Map;

public interface IAuthClient {
    Map<Descriptors.FieldDescriptor, Object> createCustomer(CustomerEntity customer, CustomerRegistrationRequest request);
}
