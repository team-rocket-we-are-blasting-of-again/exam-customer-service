package com.teamrocket.customer.service;

import com.teamrocket.customer.model.dto.TemplateDTO;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {
    // TODO DELETE
    public TemplateDTO hello(String who) {
        return new TemplateDTO(99, "Hello, " + who + "!");
    }
}
