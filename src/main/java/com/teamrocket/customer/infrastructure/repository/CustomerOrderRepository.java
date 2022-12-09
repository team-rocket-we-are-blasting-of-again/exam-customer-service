package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.RestaurantCustomerRequest;
import com.teamrocket.customer.domain.model.entity.CustomerOrderEntity;
import com.teamrocket.customer.domain.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {
    // TODO: DELETE WHEN I KNOW RESTAURANT gRPC SERVER WORKS
//    @Query("SELECT c.firstName, c.lastName, c.phone, c.addressId FROM CustomerEntity c WHERE c.customerOrder = ?1")
//    RestaurantCustomerRequest findBySystemOrderId(int systemOrderId);

    //    SELECT customer.first_name, customer.last_name, customer.phone_number, customer.address_id,
//    customer_order.created_time, customer_order.system_order_id
//    FROM customer
//    INNER JOIN customer_order ON customer.id=customer_order.customer_id
//    AND customer_order.system_order_id = 3;
//    @Query("SELECT co FROM CustomerOrderEntity co WHERE co.systemOrderId = ?1")
//    Optional<CustomerOrderEntity> test(int systemOrderId);
    Optional<CustomerOrderEntity> findCustomerOrderEntityBySystemOrderId(int systemOrderId);

    @Modifying
    @Query("UPDATE CustomerOrderEntity c SET c.status = :orderStatus WHERE c.id = :id")
    int setCustomerOrderStatus(OrderStatus orderStatus, int id);
}
