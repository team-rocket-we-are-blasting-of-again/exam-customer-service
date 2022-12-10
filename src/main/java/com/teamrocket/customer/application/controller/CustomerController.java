package com.teamrocket.customer.application.controller;

import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.domain.service.implementation.CustomerService;
import com.teamrocket.customer.domain.model.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8012") //open for specific port
@CrossOrigin() // open for all ports
@RestController
@RequestMapping(path = "/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {
    //Only autowired used in the service because of contract verifier test will fail without
    @Autowired
    private CustomerService customerService;

    /**
     * POST REQUEST
     */
    @PostMapping("/registration")
    @Transactional
    public ResponseEntity<CustomerDTO> customerRegistration(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("Create customer endpoint was hit with body: {}", customerRegistrationRequest);
        return ResponseEntity.ok(customerService.registerCustomer(customerRegistrationRequest));
    }

    /**
     * GET REQUEST
     */
    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        log.info("Get all customers endpoint was hit {}", LocalDateTime.now());
        try {
            return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Customers exception has occurred: {}",
                    "from get all customers endpoint",
                    new Exception(exception)
            );
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(value = "id") int id) {
        log.info("Get customer by id endpoint was hit with id: {}", id);
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * PUT REQUEST
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(value = "id") int id, @RequestBody CustomerEntity customer) {
        log.info("Update customer endpoint was hit with id: {}", id);
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    /**
     * DELETE REQUEST
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable(value = "id") int id) {
        log.info("Delete customer endpoint was hit with id: {}", id);
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
}
