package com.teamrocket.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamrocket.customer.model.dto.CartDTO;
import com.teamrocket.customer.model.entity.CartEntity;
import com.teamrocket.customer.service.implementation.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8012") //open for specific port
@CrossOrigin() // open for all ports
@RestController
@RequestMapping(path = "/carts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CartController {
    private final CustomerOrderService customerOrderService;

    /**
     * POST REQUEST
     */
    @PostMapping("/add-items")
    public ResponseEntity<CartDTO> addItemToCart(@RequestHeader Map<String, String> header, @RequestBody CartEntity cartEntity) throws JsonProcessingException {
        String customerId = header.get("role_id");
        log.info("Carts add items endpoint was hit. Customer has added a new item to their cart with customer id: {} and cart request contains: {}",
                customerId,
                cartEntity.getItems());
        return ResponseEntity.ok(customerOrderService.addItemToCart(customerId, cartEntity));
    }
}
