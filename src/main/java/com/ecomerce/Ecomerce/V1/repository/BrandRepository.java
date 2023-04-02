package com.ecomerce.Ecomerce.V1.repository;

import com.ecomerce.Ecomerce.V1.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    Optional<Brand> findByNameIgnoreCase(String name);
}
