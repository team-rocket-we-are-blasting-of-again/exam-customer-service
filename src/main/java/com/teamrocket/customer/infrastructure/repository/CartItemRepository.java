package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {

}
