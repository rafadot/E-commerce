package com.ecomerce.Ecomerce.V1.repository;

import com.ecomerce.Ecomerce.V1.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
