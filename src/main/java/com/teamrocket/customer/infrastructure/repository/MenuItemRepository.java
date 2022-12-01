package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {
}
