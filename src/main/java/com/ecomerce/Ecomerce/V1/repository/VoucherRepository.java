package com.ecomerce.Ecomerce.V1.repository;

import com.ecomerce.Ecomerce.V1.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
    Optional<Voucher> findByName(String name);
}
