package com.teamrocket.customer.unit.control;

import com.teamrocket.customer.control.CustomerController;
import com.teamrocket.customer.service.CustomerService;
import com.teamrocket.customer.service.TemplateService;
import com.teamrocket.customer.dto.TemplateDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ActiveProfiles("unit")
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private CustomerController customerController;

//    private TemplateDTO output;
//
//    @BeforeEach
//    void setUp() {
//        output = new TemplateDTO(); // to make the coverage happy :)
//        output = new TemplateDTO(88, "Goodbye");
//        when(templateService.hello(any())).thenReturn(output);
//    }
//
//    @AfterEach
//    void tearDown() {
//        reset(templateService);
//    }
//
//    @Test
//    void getHello() {
//        TemplateDTO actual = customerController.getHello();
//        assertNotNull(actual);
//        assertEquals(actual, output);
//
//    }
}