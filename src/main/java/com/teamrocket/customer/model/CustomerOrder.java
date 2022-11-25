package com.teamrocket.customer.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


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
    // TODO: items List<MenuItem, Integer>
    // private Map<MenuItem, Integer> items;
    @Column(name = "created_time")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date createdAt;
    @Column(name = "deliver")
    @NotNull
    private boolean deliver;
    @Column(name = "delivery_price")
    @NotNull
    private double deliveryPrice;
    @Column(name = "order_price")
    @NotNull
    private double orderPrice;
    @Column(name = "restaurant_name")
    @NotNull
    private String restaurantName;
    @Column(name = "restaurant_address")
    @NotNull
    private String restaurantAddress;
    @Column(name = "phone_number")
    @NotNull
    private String phone;
}
