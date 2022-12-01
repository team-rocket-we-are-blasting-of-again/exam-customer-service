package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {
}
