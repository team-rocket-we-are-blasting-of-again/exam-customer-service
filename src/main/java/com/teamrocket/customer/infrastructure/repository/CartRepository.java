package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

}
