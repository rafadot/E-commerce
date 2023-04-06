package com.ecommerce.Ecommerce.V1.repository;

import com.ecommerce.Ecommerce.V1.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
