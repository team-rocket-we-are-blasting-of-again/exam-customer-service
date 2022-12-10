package com.teamrocket.customer.application.controller;

import com.teamrocket.customer.domain.service.implementation.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8012") //open for specific port
@CrossOrigin() // open for all ports
@RestController
@RequestMapping(path = "/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
public class OrderController {
    @Autowired
    CustomerOrderService customerOrderService;

    /**
     * GET REQUEST
     */
    @GetMapping("/purchase")
    public ResponseEntity<String> purchaseOrderByCustomerById(@RequestHeader Map<String, String> header) {
        String customerId = header.get("role_id");
        log.info("Customer purchase orders endpoint was hit with customer id: {}", customerId);
        return ResponseEntity.ok(customerOrderService.purchaseOrder(customerId));
    }
}
