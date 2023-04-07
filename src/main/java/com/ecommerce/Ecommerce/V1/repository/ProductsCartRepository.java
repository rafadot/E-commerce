package com.ecommerce.Ecommerce.V1.repository;

import com.ecommerce.Ecommerce.V1.model.ProductsCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductsCartRepository extends JpaRepository<ProductsCart, UUID> {
    List<ProductsCart> findByAccountId(UUID accountID);
    void deleteAllByProductId(UUID productID);
    void deleteAllByAccountId(UUID accountId);
}
