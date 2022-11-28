package com.teamrocket.customer.repository;

import com.teamrocket.customer.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {
}
