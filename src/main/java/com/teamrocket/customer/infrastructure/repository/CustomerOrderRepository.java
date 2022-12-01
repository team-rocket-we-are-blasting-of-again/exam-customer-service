package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.dto.CustomerOrderDTO;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {
    @Modifying
    @Query("UPDATE CustomerOrderEntity c SET c.status = :status WHERE c.id = :id")
    CustomerOrderEntity setCustomerOrderStatus(OrderStatus orderStatus, int id);
}
