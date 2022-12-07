package com.teamrocket.customer.infrastructure.repository;

import com.teamrocket.customer.domain.model.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: probably delete this and menuitem entity
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {
}
