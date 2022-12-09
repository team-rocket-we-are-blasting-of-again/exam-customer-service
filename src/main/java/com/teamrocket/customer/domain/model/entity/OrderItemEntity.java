package com.teamrocket.customer.domain.model.entity;

import com.teamrocket.customer.domain.model.dto.OrderItem;
import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item")
public class OrderItemEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int menuItemId;
    private int quantity;

    public OrderItemEntity(CartItemEntity cartItemEntity) {
        this.menuItemId = cartItemEntity.getMenuItemId();
        this.quantity = cartItemEntity.getQuantity();
    }

    public OrderItemEntity(OrderItem dto) {
        this.menuItemId = dto.getMenuItemId();
        this.quantity = dto.getQuantity();
    }
}
