package com.teamrocket.customer.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {

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
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "street_number", nullable = false)
    private String streetNumber;
    @Column(name = "zipcode", nullable = false)
    private String zipCode;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    @Column(name = "door")
    private String door;

    @OneToMany(mappedBy = "address")
    private List<Customer> customerList;


}
