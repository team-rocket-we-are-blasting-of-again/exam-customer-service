package com.teamrocket.customer.repository;

import com.teamrocket.customer.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
