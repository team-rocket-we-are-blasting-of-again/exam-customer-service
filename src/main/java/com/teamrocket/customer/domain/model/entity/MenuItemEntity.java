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
@Table(name = "menu_item")
public class MenuItemEntity {
    @Id
    @SequenceGenerator(
            name = "menu_item_id_sequence",
            sequenceName = "menu_item_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "menu_item_id_sequence"
    )
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
//    @ManyToOne(mappedBy = "menuItemId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<CustomerOrderEntity> items;
//    @ManyToMany(mappedBy = "menuItemEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<CustomerOrderEntity> items;
}
