package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {
    @Modifying
    @Query("UPDATE CustomerOrderEntity c SET c.status = :orderStatus WHERE c.id = :id")
    int setCustomerOrderStatus(String orderStatus, int id);
}
