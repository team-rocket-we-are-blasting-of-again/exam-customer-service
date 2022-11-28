package com.teamrocket.customer.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamrocket.customer.dto.NewOrder;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.service.implementation.CamundaService;
import com.teamrocket.customer.service.implementation.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CamundaService camundaService;


    /**
     * POST REQUEST
     */
    @PostMapping("/customer")
    @Transactional
    public ResponseEntity<Customer> customerRegistration(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("New customer registered {}", customerRegistrationRequest);
        return ResponseEntity.ok(customerService.registerCustomer(customerRegistrationRequest));
    }

    @PostMapping("/customer/new-order")
    public ResponseEntity<String> createNewOrder(@RequestHeader Map<String, String> header, @RequestBody NewOrder newOrder) throws JsonProcessingException {
        String customerId = header.get("role_id");
        return ResponseEntity.ok(camundaService.startOrderProcess(customerId, newOrder));
    }

    /**
     * GET REQUEST
     */
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        log.info("All customers were fetched {}", LocalDateTime.now());
        try {
            return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int id) {
        log.info("Customers was fetched with id: {}", id);
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * PUT REQUEST
     */
    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") int id, @RequestBody Customer customer) {
        System.out.println("PATH VARIABLE FOR PUT: " + id);
        log.info("Customers was updated with id: {}", id);
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    /**
     * DELETE REQUEST
     */
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable(value = "id") int id) {
        log.info("Customers was deleted with id: {}", id);
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }

}
