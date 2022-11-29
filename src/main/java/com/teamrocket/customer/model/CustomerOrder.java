package com.teamrocket.customer.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_order")
public class CustomerOrder {

    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    private int id;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_order_menu_item",
            joinColumns = @JoinColumn(name = "menu_item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_order_id",
                    referencedColumnName = "id"))
    private List<MenuItem> menuItem;
    @Column(name = "created_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Column(name = "deliver", nullable = false)
    private boolean deliver;
    @Column(name = "delivery_price", nullable = false)
    private double deliveryPrice;
    @Column(name = "order_price", nullable = false)
    private double orderPrice;
    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;
    @Column(name = "restaurant_address", nullable = false)
    private String restaurantAddress;
    @Column(name = "phone_number", nullable = false)
    private String phone;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
