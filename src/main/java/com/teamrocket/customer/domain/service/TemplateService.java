package com.teamrocket.customer.domain.service;

import com.teamrocket.customer.domain.model.dto.TemplateDTO;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {
    // TODO DELETE
    public TemplateDTO hello(String who) {
        return new TemplateDTO(99, "Hello, " + who + "!");
    }
}