package com.teamrocket.customer.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamrocket.customer.model.dto.NewOrder;

public interface ICamundaService {
    String startOrderProcess(String customerId, NewOrder newOrder) throws JsonProcessingException;
}
