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


    @ManyToMany
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
    @Column(name = "customer", nullable = false)
    @ManyToOne
    private Customer customer;

}
