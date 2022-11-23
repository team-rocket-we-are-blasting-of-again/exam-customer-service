package com.teamrocket.customer.control;

import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.service.CustomerService;
import com.teamrocket.customer.service.TemplateService;
import com.teamrocket.customer.dto.TemplateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
//@CrossOrigin(origins = "http://localhost:8012") //open for specific port
@CrossOrigin() // open for all ports
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
//@RequiredArgsConstructor
//public class CustomerController {
public record CustomerController(CustomerService customerService) {
// TODO: REMOVE comments when controller is done

//    private final TemplateService templateService;
//
//    @GetMapping
//    public TemplateDTO getHello() {
//        return templateService.hello("You");
//    }

    /**
     * POST REQUEST
     */

    @PostMapping("/customer")
    @Transactional
    public ResponseEntity<Customer> customerRegistration(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("New customer registered {}", customerRegistrationRequest);
        return customerService.registerCustomer(customerRegistrationRequest);
    }

    /**
     * GET REQUEST
     */

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        log.info("All customers were fetched {}", LocalDateTime.now());
        return customerService.getCustomers();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int id) {
        log.info("Customers was fetched with id: {}", id);
        return customerService.getCustomerById(id);
    }

    /**
     * PUT REQUEST
     */

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") int id, @RequestBody Customer customer) {
        log.info("Customers was updated with id: {}", id);
        return customerService.updateCustomer(id, customer);
    }

    /**
     * DELETE REQUEST
     */
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable(value = "id") int id) {
        log.info("Customers was deleted with id: {}", id);
        return customerService.deleteCustomer(id);
    }
}
