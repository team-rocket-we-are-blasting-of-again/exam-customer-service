package com.teamrocket.customer.service;

import com.teamrocket.VerifiedUser;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;

public interface IRPCService {

    VerifiedUser verifyCustomer(Customer customer, CustomerRegistrationRequest request);

}
