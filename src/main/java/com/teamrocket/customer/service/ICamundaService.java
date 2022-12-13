package com.teamrocket.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamrocket.customer.model.dto.NewOrder;

public interface ICamundaService {
    String startOrderProcess(String customerId, NewOrder newOrder) throws JsonProcessingException;
}
