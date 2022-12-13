package com.teamrocket.customer.repository;

import com.teamrocket.customer.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    CustomerEntity findByEmail(String email);
}
