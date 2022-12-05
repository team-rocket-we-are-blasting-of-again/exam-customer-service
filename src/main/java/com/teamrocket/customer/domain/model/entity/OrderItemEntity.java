package com.teamrocket.customer.domain.model.entity;

import com.teamrocket.customer.domain.model.dto.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item")
public class OrderItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "order_item_id_sequence",
            sequenceName = "order_item_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_id_sequence"
    )
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
