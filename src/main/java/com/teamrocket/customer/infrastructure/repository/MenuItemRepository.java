package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.model.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {
}
