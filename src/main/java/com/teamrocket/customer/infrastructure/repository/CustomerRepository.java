package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    CustomerEntity findByEmail(String email);
}
