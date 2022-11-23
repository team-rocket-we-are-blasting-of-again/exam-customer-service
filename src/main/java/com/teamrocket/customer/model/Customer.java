package com.teamrocket.customer.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

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
    private String
            firstName, lastName, email;
}
