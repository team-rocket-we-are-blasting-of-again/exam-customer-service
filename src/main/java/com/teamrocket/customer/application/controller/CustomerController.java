package com.teamrocket.customer.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import com.teamrocket.customer.domain.model.CustomerRegistrationRequest;
import com.teamrocket.customer.domain.model.entity.CartEntity;
import com.teamrocket.customer.domain.service.implementation.CamundaService;
import com.teamrocket.customer.domain.service.implementation.CustomerOrderService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8012") //open for specific port
@CrossOrigin() // open for all ports
@RestController
@RequestMapping(path = "/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CamundaService camundaService;
    @Autowired
    CustomerOrderService customerOrderService;


    /**
     * POST REQUEST
     */
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<CustomerDTO> customerRegistration(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("Create customer endpoint was hit with body: {}", customerRegistrationRequest);
        return ResponseEntity.ok(customerService.registerCustomer(customerRegistrationRequest));
    }

    @PostMapping("/add-item-to-cart")
    public ResponseEntity<CartEntity> addItemToCart(@RequestHeader Map<String, String> header, @RequestBody CartEntity cartEntity) throws JsonProcessingException {
        String customerId = header.get("role_id");
        log.info("Add item to cart endpoint was hit and customer has added a new item to their cart with customer id: {}",
                customerId);
        return ResponseEntity.ok(customerOrderService.addItemToCart(customerId, cartEntity));
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

    @GetMapping("/purchase-order")
    public ResponseEntity<String> getCustomerById(@RequestHeader Map<String, String> header) {
        String customerId = header.get("role_id");
//        log.info("Get customer by id endpoint was hit with id: {}", customerId);
        return ResponseEntity.ok(customerOrderService.purchaseOrder(customerId));
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
