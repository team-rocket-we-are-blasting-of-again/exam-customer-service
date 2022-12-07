package com.teamrocket.customer.domain.model.entity;

import com.teamrocket.customer.domain.model.dto.CustomerDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer", uniqueConstraints = {@UniqueConstraint(columnNames = "email"), @UniqueConstraint(columnNames = "phone_number")})
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(
//            name = "customer_id_sequence",
//            sequenceName = "customer_id_sequence"
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "customer_id_sequence"
//    )
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "address_id", nullable = false)
    private int addressId;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phone;
    //    @OneToMany(mappedBy = "customer",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//            fetch = FetchType.EAGER)
//    orphanRemoval =true)
//    @ElementCollection(fetch = FetchType.EAGER)
//
//    @CollectionTable(name = "customer",
//            joinColumns = {@JoinColumn(name = "customer_id",
//                    referencedColumnName = "id")})
//    @MapKeyJoinColumn(name = "customer_order_id")
//    @Column(name = "customer_order")
    @OneToMany(targetEntity = CustomerOrderEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinColumn(name = "co_fk", referencedColumnName = "id")
    private List<CustomerOrderEntity> customerOrderEntity = new ArrayList<>();
}
