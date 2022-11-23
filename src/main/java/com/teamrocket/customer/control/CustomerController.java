package com.teamrocket.customer.control;

import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.service.CustomerService;
import com.teamrocket.customer.service.TemplateService;
import com.teamrocket.customer.dto.TemplateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
//@RequiredArgsConstructor
//public class CustomerController {
public record CustomerController(CustomerService customerService) {

//    private final TemplateService templateService;
//
//    @GetMapping
//    public TemplateDTO getHello() {
//        return templateService.hello("You");
//    }

    @PostMapping
    public void customerRegistration(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("New customer registered {}", customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);
    }
}
