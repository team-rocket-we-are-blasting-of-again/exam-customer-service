package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {

}
