package com.teamrocket.customer.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @SequenceGenerator(
            name = "orderItem_id_sequence",
            sequenceName = "orderItem_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orderItem_id_sequence"
    )
    private int id;
    private int menuItemId;
    private int quantity;
    @ManyToMany(mappedBy = "menuItemEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CustomerOrderEntity> items;
}
