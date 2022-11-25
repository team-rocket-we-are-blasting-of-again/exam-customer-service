package com.teamrocket.customer.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamrocket.customer.dto.NewOrder;
import com.teamrocket.customer.model.Customer;
import com.teamrocket.customer.model.CustomerRegistrationRequest;
import com.teamrocket.customer.service.CamundaService;
import com.teamrocket.customer.service.CustomerService;
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
@RequiredArgsConstructor
public class CustomerController {

    // TODO: REMOVE comments when controller is done
    //public record CustomerController(CustomerService customerService) {


    private final CustomerService customerService;

    private final CamundaService camundaService;

    /**
     * POST REQUEST
     */

    @PostMapping("/customer")
    @Transactional
    public ResponseEntity<Customer> customerRegistration(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("New customer registered {}", customerRegistrationRequest);
        return customerService.registerCustomer(customerRegistrationRequest);
    }

    @PostMapping("/customer-new-order")
    public String createNewOrder(@RequestHeader Map<String, String> header, @RequestBody NewOrder newOrder) throws JsonProcessingException {
        String customerId = header.get("role_id");
        return camundaService.startOrderProcess(customerId, newOrder);
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
