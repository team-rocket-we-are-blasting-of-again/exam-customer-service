package com.teamrocket.customer.repository;

import com.teamrocket.customer.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {
    Optional<CustomerOrderEntity> findCustomerOrderEntityBySystemOrderId(int systemOrderId);

    @Modifying
    @Query("UPDATE CustomerOrderEntity c SET c.status = :orderStatus WHERE c.id = :id")
    int setCustomerOrderStatus(OrderStatus orderStatus, int id);
}
