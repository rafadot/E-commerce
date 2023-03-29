package com.ecomerce.Ecomerce.V1.repository;

import com.ecomerce.Ecomerce.V1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
