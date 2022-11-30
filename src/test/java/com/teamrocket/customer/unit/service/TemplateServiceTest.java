package com.teamrocket.customer.unit.service;

import com.teamrocket.customer.domain.service.TemplateService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TemplateServiceTest {

    @Autowired
    TemplateService templateService;
    private String who = "Me";

    @Test
    void helloTest() {
        assertEquals(templateService.hello("Me").getMsg(), "Hello, " + who + "!");
        assertEquals(templateService.hello("Me").getId(), 99);
    }
}